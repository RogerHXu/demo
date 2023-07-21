package com.example.demo.application;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
public class Doc {
    @Id
    private String id;

    private String title;

    private Binary file;

    public Doc(String title){
        this.title = title;
    }

}
