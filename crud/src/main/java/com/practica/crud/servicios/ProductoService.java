package com.practica.crud.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.practica.crud.entidades.Foto;
import com.practica.crud.entidades.Producto;
import com.practica.crud.errores.ErrorService;
import com.practica.crud.repositorios.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private FotoService fotoService;
	
	//VALIDAR NOMBRE
		@Transactional
		private void validarNombre(String nombre) throws ErrorService {
			
			if(nombre==null || nombre.isEmpty()) {
				throw new ErrorService("El nombre no puede estar vacio");
			}
		}
	
	
	//GUARDAR   
	@Transactional 
	public void guardar(MultipartFile archivo, String nombre, Integer cantidad, Long precio) throws ErrorService {
		
		try {
			Producto prod = new Producto();
			
			prod.setNombre(nombre);
			prod.setCantidad(cantidad);
			prod.setPrecio(precio);
			
			productoRepository.save(prod);
			
			Foto foto = fotoService.guardar(archivo);
			prod.setFoto(foto); 
			
			productoRepository.save(prod);
		} catch (Exception e) {
			System.out.println("Error al guardar producto.");
			e.printStackTrace();     
		}
		
	}
	
	//LISTAR PRODUCTOS
	@Transactional(readOnly=true) //Se usa por buenas practicas aclarando entre parentesis que no es una modificacion a la BBDD
	public List<Producto> listarProductos(){
		return productoRepository.findAll();
	}
	
	
	//MODIFICACION
		public void modificarProducto(MultipartFile archivo, String id, String nombre, Long precio, Integer cantidad) throws ErrorService{
			
			validarNombre(nombre);
			
			try {
				Optional<Producto> respuesta = productoRepository.findById(id); // optional allows you to use isPresent 
				if(respuesta.isPresent()){ // if the value is present returns true
					Producto prod = respuesta.get(); // .get() returns the value (and here, it will get stored into "prod") 
					prod.setNombre(nombre);
					prod.setPrecio(precio);
					prod.setCantidad(cantidad);
					
					
					String idFoto = null; //set idFoto as null
					if(prod.getFoto() != null) { //if there already was a photo,...
						idFoto = prod.getFoto().getId(); //...set idFoto with the previous photo's id
					}
					
					Foto foto = fotoService.actualizar(idFoto, archivo); //if it's the first time it receives a photo this will create it
					//if there was already one, it'll be updated
					prod.setFoto(foto); //This saves the changes
					
					productoRepository.save(prod);
				}
				
			} catch (Exception e) {
				System.out.println("ProductoService: Error al modificar producto.");
				e.printStackTrace();                              
			}
			
		}
	
		
		//ELIMINAR 
	public void eliminarProducto(String id) throws ErrorService{
		try {
			Optional<Producto> respuesta = productoRepository.findById(id);
			if(respuesta.isPresent()){ 
				Producto prod = respuesta.get(); 
				productoRepository.delete(prod);
			}
			
		} catch (Exception e) {			
			System.out.println("ProductoService: Error al eliminar producto.");
			e.printStackTrace();
		}
	}
		
		//RETORNAR PRODUCTO POR ID 
	public Producto retornarProductoPorId(String id) {
		return (Producto) productoRepository.getById(id);
	}
	
}
