package com.example.demo.repository;

import com.example.demo.application.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<Application, String> {

}
