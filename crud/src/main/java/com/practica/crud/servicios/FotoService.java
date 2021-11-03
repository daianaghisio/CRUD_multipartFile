package com.practica.crud.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.practica.crud.entidades.Foto;
import com.practica.crud.errores.ErrorService;
import com.practica.crud.repositorios.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository repo;
	
	public Foto guardar(MultipartFile archivo) throws ErrorService{
		if(archivo != null) {
			try {
			
		     	Foto foto = new Foto(); //creates the new picture
			    foto.setMime(archivo.getContentType()); // sets mime type
			    foto.setNombre(archivo.getName()); //sets the new image's name
			    foto.setContenido(archivo.getBytes()); //sets the content
			    
			    //"getBytes" might throw exception, we need to set a try-catch block
			
			    return repo.save(foto); //returns the persisted image
			
			} catch(Exception e) {
				System.err.println(e.getMessage()); //this is used to display an error message on the console
			}
		}
			return null;
		}
	
	
	public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorService{
		if(archivo != null) {
			try {
			
		     	Foto foto = new Foto(); 
		     	
		     	if(idFoto != null) { // to make sure there is one to update
		     		Optional<Foto> respuesta = repo.findById(idFoto);
		     		if(respuesta.isPresent()) {
		     			foto = respuesta.get();
		     	    }
		     	}
		     	
			    foto.setMime(archivo.getContentType()); 
			    foto.setNombre(archivo.getName()); 
			    foto.setContenido(archivo.getBytes());
			
			    return repo.save(foto); //persists and returns the changed image
			
			} catch(Exception e) {
				System.err.println(e.getMessage()); 
			}
		}
			return null;
		}
	
	
	} //FotoService class ends here
	

