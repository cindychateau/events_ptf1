package com.codingdojo.cynthia.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.Message;

@Repository
public interface MessagesRepository extends CrudRepository<Message, Long> {
	
	List<Message> findAll(); //SELECT * FROM messages
	
}
