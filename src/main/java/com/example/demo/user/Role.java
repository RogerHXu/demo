package com.example.demo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Setter
@Getter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private ERole name;

    public Role(ERole name){
        this.name = name;
    }

}
