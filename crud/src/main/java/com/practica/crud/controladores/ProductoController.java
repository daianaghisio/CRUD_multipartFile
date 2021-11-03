package com.practica.crud.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.practica.crud.entidades.Producto;
import com.practica.crud.repositorios.ProductoRepository;
import com.practica.crud.servicios.ProductoService;



@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	@Autowired
	ProductoRepository productoRepository;

	
	
	
	
	@GetMapping("/")
	public String indexProductos(Model model) { 
		try {
			String titulo = "Inventario";
			
			model.addAttribute("titulo", titulo);				
			model.addAttribute("productos", productoService.listarProductos());
			
		} catch (Exception e) {
			System.out.println("ProductoController: Error al devolver indexProductos");
			e.printStackTrace();
		}

		return "productos";
	}
	 
	
	@GetMapping("/productos-crear")
	public String crearProducto(Model model) {
		try {
			String titulo = "Pagina para llenar Inventario";
			List<Producto> productos = productoService.listarProductos();
			//	Producto prod = new Producto();
			
			model.addAttribute("producto", new Producto());			
			model.addAttribute("titulo", titulo);
			model.addAttribute("productos", productos); 
			
		} catch (Exception e) {
			System.out.println("ProductoController: Error al crear producto");
			e.printStackTrace();
		}
		
		return "productos-crear"; 
	}
	
	@PostMapping("/productos-crear")
	public String save(Model model, MultipartFile archivo, String nombre, Integer cantidad, Long precio) {
		try {					
			
			productoService.guardar(archivo, nombre, cantidad, precio);

		} catch (Exception e) {
			System.out.println("ProductoController: Error al guardar producto");
			
			e.printStackTrace();
		}

		return "redirect:/productos/";
	}
	
	
	@GetMapping("/editar/{id}")
	public String edit(Model model, @PathVariable String id) {
		try {					
			String titulo = "Pagina de edicion de productos";
			model.addAttribute("titulo", titulo);
			
		    Producto producto = productoService.retornarProductoPorId(id); 
			model.addAttribute("productoSeleccionado", producto);
			
		} catch (Exception e) {
			System.out.println("ProductoController: Error al guardar producto");
			
			e.printStackTrace();
		}

		return "productos-editar";
	}
	
	@PostMapping("/editar/{id}")
	public String edit(Model model, MultipartFile archivo, @PathVariable String id, String nombre, Long precio, Integer cantidad) {
		try {			
			productoService.modificarProducto(archivo, id, nombre, precio, cantidad);
			
		} catch (Exception e) {
			System.out.println("ProductoController: Error al guardar producto");			
			e.printStackTrace();
		}

		return "redirect:/productos/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model model, Producto a) {
		productoRepository.delete(a);
		return "redirect:/productos/";
	}

	
	
	
	
} 