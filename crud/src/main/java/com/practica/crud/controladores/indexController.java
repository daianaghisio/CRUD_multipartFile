package com.practica.crud.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexController {

	@GetMapping("/")
	public String index(Model model) {
		String titulo = "Inventario!";
		model.addAttribute("titulo", titulo); 
		return "index";
	}
}
