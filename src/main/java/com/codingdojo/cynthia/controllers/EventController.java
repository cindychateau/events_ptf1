package com.codingdojo.cynthia.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.cynthia.models.Event;
import com.codingdojo.cynthia.models.Message;
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
		
		//enviar el usuario
		User myUser = service.findUser(userInMethod.getId());
		model.addAttribute("user", myUser);
		
		//enviar la lista de eventos en mi estado
		String myState = userInMethod.getState(); //Obteniendo el estado del usuario en sesion
		List<Event> eventsMyState = service.eventsInMyState(myState);
		model.addAttribute("eventsMyState", eventsMyState);
		
		//enviar la lista de eventos en otros estados
		List<Event> eventsOtherState = service.eventsOther(myState);
		model.addAttribute("eventsOtherState", eventsOtherState);
		
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
	
	@GetMapping("/join/{eventId}")
	public String join(@PathVariable("eventId") Long eventId,
					   HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		service.joinEvent(userInMethod.getId(), eventId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/remove/{eventId}")
	public String remove(@PathVariable("eventId") Long eventId,
						 HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		service.removeEvent(userInMethod.getId(), eventId);
		return "redirect:/dashboard";
		
	}
	
	@GetMapping("/event/{eventId}")
	public String show_event(@PathVariable("eventId") Long eventId,
							 HttpSession session,
							//Agregamos modelattribute porque aquí mismo hay un formulario de Mensaje
							 @ModelAttribute("message") Message message,
							 Model model) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		Event event = service.findEvent(eventId); //Enviamos el evento en base a su id
		model.addAttribute("event", event);
		
		return "show.jsp";
		
	}
	
	@PostMapping("/message")
	public String message(@Valid @ModelAttribute("message") Message message,
						  BindingResult result,
						  HttpSession session,
						  Model model) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		if(result.hasErrors()) {
			model.addAttribute("event", message.getEvent());
			return "show.jsp";
		} else {
			service.saveMessage(message);
			//Regresamos ala misma pantalla, por lo tanto quiero enviar en la url el id del evento
			//Como recibo objeto message, mi objeto message tiene un evento, obtengo el id de este
			return "redirect:/event/"+message.getEvent().getId();
		}
		
		
	}
	
	@DeleteMapping("/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") Long eventId, HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		Event event = service.findEvent(eventId);
		
		//event == null -> Si el evento es null significa que NO existe ese evento (no hay evento con ese id)
		//!event.getPlanner().getId().equals(userInMethod.getId())
		//Si el id de la persona que planeó el evento es diferente al id del usuario en sesión
		//Si cualquiera de las dos condiciones se cumple, regresa a dashboard y no borra nada
		if(event == null || !event.getPlanner().getId().equals(userInMethod.getId())) {
			return "redirect:/dashboard";
		}
		
		service.deleteEvent(eventId);
		
		return "redirect:/dashboard";
	}
	
	
}
