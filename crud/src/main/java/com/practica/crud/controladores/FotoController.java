package com.practica.crud.controladores;

import java.util.logging.Level;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practica.crud.entidades.Producto;
import com.practica.crud.errores.ErrorService;
import com.practica.crud.servicios.ProductoService;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private ProductoService productoService;
	
	
	
	@GetMapping("/producto")
	public ResponseEntity<byte[]> fotoProducto(@RequestParam String id) throws ErrorService {
		//Objetivo de este metodo: devolver una foto vinculada a un producto
		
	try {
		Producto producto = productoService.retornarProductoPorId(id);
		
		if(producto.getFoto() == null) {
			throw new ErrorService("El producto no tiene una foto asignada.");
		}
		
		byte[] foto = producto.getFoto().getContenido();
	
		
		HttpHeaders headers = new HttpHeaders(); //headers le dice al navegador que lo que estoy devolviendo es una imagen
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<>(foto, headers, HttpStatus.OK); 
	} catch (Exception e) {
		System.out.println("FotoController: No se encontro el id de la foto");
		e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
		
	}
	
}
