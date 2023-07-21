package com.example.demo.service;

import com.example.demo.application.Application;
import com.example.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public List<Application> searchByText(String text) {
        final List<Application> posts = new ArrayList<Application>();
        return posts;
    }

    @Override
    public String insertApplication(Application app) {
        applicationRepository.save(app);
        return null;
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }


}
