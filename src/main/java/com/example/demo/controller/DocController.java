package com.example.demo.controller;

import com.example.demo.application.Doc;
import com.example.demo.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocController {

    @Autowired
    private DocService documentService;

    @PostMapping("add")
    public String addDocument(@RequestParam("title") String title, @RequestParam("Document")MultipartFile file) throws Exception{
        String id = documentService.addDocument(title, file);

        return "redirect:/documents/" + id;
    }

    @GetMapping("/{id}")
    public String getDocument(@PathVariable String id, Model model){
        Doc doc = documentService.getDocument(id);
        model.addAttribute("title", doc.getTitle());
        model.addAttribute("file", Base64.getEncoder().encodeToString(doc.getFile().getData()));
        return "ASSSS";
    }

    @GetMapping("/all")
    public List<Doc> getAllDocument(){
        List<Doc> docs = documentService.getAllDocument();
        return docs;
    }

    @PostMapping("remove")
    public void removeDocument(@RequestParam("id") String id){
        documentService.deleteDoc(id);
        return;
    }
}
