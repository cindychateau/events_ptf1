package com.codingdojo.cynthia.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.Event;

@Repository
public interface EventsRepository extends CrudRepository<Event, Long> {
	
	//Lista de eventos de mi estado
	//SELECT * FROM events WHERE event_state = <ESTADO RECIBIDO>
	List<Event> findByEventState(String state); 
 	
	//Lista de eventos del resto de los estados
	//SELECT * FROM events WHERE event_state != <ESTADO RECIBIDO>
	List<Event> findByEventStateIsNot(String state);
	
}
