package com.example.demo.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "applications")
public class Application {
    @Id
    private String id;

    @NotBlank
    private String job_title;

    @NotBlank
    private String company_name;

    @NotBlank
    private LocalDateTime date;

    private String location;

    private String comment;

    @NotBlank
    private EStatus status;

    private boolean interviewed;

    @DBRef
    private Set<Doc> docs;

    private List<String> links;
}
