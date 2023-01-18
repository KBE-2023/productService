package com.example.helloworld.service;

import com.example.helloworld.model.ABC;
import com.example.helloworld.repo.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository cdto;


    String line ="";

    public void saveComponentData(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            while((line = br.readLine())!=null){
                String [] data=line.split(",");
                ABC c = new ABC();
                c.setName(data[0]);
                c.setPrice(data[1]);
                c.setDescription(data[2]);
                c.setImage(data[3]);
                cdto.save(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}