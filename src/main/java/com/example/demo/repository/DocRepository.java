package com.example.demo.repository;

import com.example.demo.application.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocRepository extends MongoRepository<Doc, String> {
    void deleteById(String id);
}
