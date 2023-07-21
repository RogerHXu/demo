package com.example.demo.service;

import com.example.demo.application.Doc;
import com.example.demo.repository.DocRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocService {
    @Autowired
    private DocRepository resumeRepository;

    public String addDocument(String title, MultipartFile file) throws IOException{
        Doc resume = new Doc(title);
        resume.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        resume = resumeRepository.insert(resume);
        return resume.getId();
    }

    public Doc getDocument(String id){
        return resumeRepository.findById(id).get();
    }

    public List<Doc> getAllDocument(){
        return resumeRepository.findAll();
    }

    public void deleteDoc(String id){
        resumeRepository.deleteById(id);
        return;
    }
}
