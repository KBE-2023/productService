package com.kbe2223.productservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double product;
    private String description;
    private String image;
    private String name;
    private double price;

    public Product() {}

    public Product(String description, String image, String name, Double price){
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getDescription(){
        return this.description;
    }

    public String getImage(){
        return this.image;
    }

    public String getName(){
        return this.name;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setName(String name){
        this.name = name;
    }



    public Double getPrice(){
        return this.price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
