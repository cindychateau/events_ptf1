package com.codingdojo.cynthia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.Event;

@Repository
public interface EventsRepository extends CrudRepository<Event, Long> {
	
	//Lista de eventos de mi estado
	
	//Lista de eventos del resto de los estados
	
}
