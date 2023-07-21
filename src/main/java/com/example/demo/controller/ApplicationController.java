package com.example.demo.controller;

import com.example.demo.application.Application;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.service.ApplicationServiceImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import jakarta.validation.Valid;
import org.bson.json.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired
    ApplicationServiceImpl appServiceImpl;

    //MongoCollection<Application> collection = db.getCollection("my_events", Event.class);

    /*@Autowired
    private MongoTemplate template;*/

    @GetMapping
    public List<Application> getAllApplications(){
        return appServiceImpl.findAll();
    }

    @GetMapping("/{text}")
    public List<Application> search(@PathVariable String text){
        return null;
    }

    @PostMapping("/edit")
    public String createApplication(@Valid @RequestBody Application app){
        try {
            appServiceImpl.insertApplication(app);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "temp";
    }

   /* @GetMapping("/{text}")
    public void searchByMonth(@Valid @RequestBody LocalDateTime from, @Valid @RequestBody LocalDateTime to){
        /*BasicDBObject object = new BasicDBObject();
        object.put("date", BasicDBObjectBuilder.start("$gte", from).add("$lte", to).get());
        List<Application> list = new ArrayList(appServiceImpl.find(object).into(new ArrayList<>()));
        Query query = new Query();
        query.addCriteria(Criteria.where("date").gte(from).lte(to));
        return temp.find(query, Application.class);
        return;
    }*/




}
