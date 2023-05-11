package com.codingdojo.cynthia.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.services.AppService;

@Controller
public class EventController {
	
	@Autowired
	private AppService service;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
		
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		
		return "dashboard.jsp";
	}
	
}
