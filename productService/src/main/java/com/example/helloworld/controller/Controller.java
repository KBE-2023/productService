package com.example.helloworld.controller;

import com.example.helloworld.model.ABC;
import com.example.helloworld.repo.FetchDataService;
import com.example.helloworld.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path="/kbe")
public class Controller {

    @Autowired
    private ComponentService ds;

    @Autowired
    private FetchDataService fs;

    @RequestMapping(path="/data")
    public void setDataInDB(){
      ds.saveComponentData();
    }


    @GetMapping(path = "/getdata")
    List<ABC> getModul(){
        return fs.findAll();
    }


}
