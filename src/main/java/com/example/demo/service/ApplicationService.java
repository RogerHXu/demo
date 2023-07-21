package com.example.demo.service;

import com.example.demo.application.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> searchByText(String text);

    String insertApplication(Application app);

    List<Application> findAll();

}
