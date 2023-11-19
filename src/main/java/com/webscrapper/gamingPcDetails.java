package com.webscrapper;

import javax.persistence.*;

/** Class modelling of a  Gaming Laptop */
@Entity
@Table(name="gaming_pc_details")
public class gamingPcDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    //Title of degree programme
    @Column(name = "name")
    String name;

    //Description of degree programme
    @Column(name = "brand")
    String brand;

    //Title of degree programme
    @Column(name = "model")
    String model;

    //Description of degree programme
    @Column(name = "image_url")
    String imageurl;

    //Description of Gaming Laptop
    @Column(name = "description")
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
