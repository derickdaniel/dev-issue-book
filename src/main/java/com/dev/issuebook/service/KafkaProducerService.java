package com.dev.issuebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dev.issuebook.dto.TagAssignmentDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "tagassignment";
    
    @Autowired
    private KafkaTemplate<String, TagAssignmentDTO> kafkaTemplate;

    @CircuitBreaker(name = "kafkaSendCB", fallbackMethod = "sendMessageFallback")
    public void sendMessage(TagAssignmentDTO dto) {
        kafkaTemplate.send(TOPIC, dto);
        System.out.println("Message sent: " + dto);
    }
    
    public void sendMessageFallback(Exception ex) {
    	System.out.println("Kafka is down!!" + ex.getMessage());
    }
}