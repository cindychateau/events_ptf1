package com.codingdojo.cynthia.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.cynthia.models.Event;
import com.codingdojo.cynthia.models.State;
import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.services.AppService;

@Controller
public class EventController {
	
	@Autowired
	private AppService service;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,
							@ModelAttribute("newEvent") Event newEvent,
							Model model) {
		
		/*Revisa que mi usuario haya iniciado sesión*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*Revisa que mi usuario haya iniciado sesión*/
		
		//Pendiente enviar el usuario
		
		//Pendiente enviar la lista de eventos en mi estado
		
		//Pendiente enviar la lista de eventos en otros estados
		
		//model.addAttribute("states", State.States);
		model.addAttribute("states", State.States);
		
		return "dashboard.jsp";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("newEvent") Event newEvent,
						 BindingResult result,
						 HttpSession session,
						 Model model) {
		
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		//Revisamos si hay errores en evento
		if(result.hasErrors()) {
			model.addAttribute("states", State.States);
			return "dashboard.jsp";
		} else {
			service.saveEvent(newEvent);
			return "redirect:/dashboard";
		}
		
		
	}
	
}
