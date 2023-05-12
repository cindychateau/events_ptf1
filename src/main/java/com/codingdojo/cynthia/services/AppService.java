package com.codingdojo.cynthia.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cynthia.models.Event;
import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.repositories.EventsRepository;
import com.codingdojo.cynthia.repositories.MessagesRepository;
import com.codingdojo.cynthia.repositories.UsersRepository;

@Service
public class AppService {
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private EventsRepository eventRepo;
	
	@Autowired
	private MessagesRepository messageRepo;
	
	public User register(User newUser, BindingResult result) {
		
		//Revisamos que el correo que recibimos no exista en nuestra tabla de usuarios
		String email = newUser.getEmail();
		User isUser = userRepo.findByEmail(email); //NULL o Objeto Usuario
		if(isUser != null) {
			//El correo ya está registrado
			//result.rejectValue("email", "Unique", "The email is already in use");
			result.rejectValue("email", "Unique", "The email is already in use");
		}
		
		//Comparamos contraseñas 
		String password = newUser.getPassword();
		String confirm = newUser.getConfirm();
		if(!password.equals(confirm)) { //! -> Lo contrario
			//result.rejectValue("confirm", "Matches", "The passwords don't match");
			result.rejectValue("confirm", "Matches", "The passwords don't match");
		}
		
		//Si NO existe error, guardamos!
		if(result.hasErrors()) {
			return null;
		} else {
			//Encriptamos contraseña
			//String contra_encriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
			String pass_encript = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(pass_encript);
			return userRepo.save(newUser);
		}	
		
	}
	
	public User login(String email, String password) {
		
		//Buscar que el correo recibido esté en BD
		User userExists = userRepo.findByEmail(email); //NULL o Objeto de User
		if(userExists == null) {
			return null;
		}
		
		//Comparamos contraseñas
		//BCrypt.checkpw(Contraseña NO encriptada, Contraseña encriptada) -> True o False
		if(BCrypt.checkpw(password, userExists.getPassword())) {
			return userExists;
		} else {
			return null;
		}
		
	}
	
	//Recibe objeto evento y guarda en BD
	public Event saveEvent(Event newEvent) {
		return eventRepo.save(newEvent);
	}
	
	public User findUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	public List<Event> eventsInMyState(String state){
		return eventRepo.findByEventState(state);
	}
	
	public List<Event> eventsOther(String state) {
		return eventRepo.findByEventStateIsNot(state);
	}
	
	public Event findEvent(Long id) {
		return eventRepo.findById(id).orElse(null);
	}
	
	/*
	 * userId = 1
	 * eventId = 2
	 * myUser = Obj(Elena) ->firstName, lastName.... eventsAttending
	 * 
	 * eventsAttending = [Obj(Java Party)] + myEvent
	 * eventsAttending = [Obj(JavaParty), Obj(Examen Java)]
	 */
	public void joinEvent(Long userId, Long eventId) {
		User myUser = findUser(userId);  //OBjeto usuario
		Event myEvent = findEvent(eventId); //Objeto evento
		
		//Me obtiene la lista de eventos que va a ir mi usuario y le agrega el objeto evento que recibo
		myUser.getEventsAttending().add(myEvent);
		userRepo.save(myUser);
	}
	
	public void removeEvent(Long userId, Long eventId) {
		User myUser = findUser(userId);  //OBjeto usuario
		Event myEvent = findEvent(eventId); //Objeto evento
		
		myUser.getEventsAttending().remove(myEvent);
		userRepo.save(myUser);
	}
	
}
