package com.webscrapper;

import javax.persistence.*;

/** Class representing a Gaming Laptop */
@Entity
@Table(name="gaming_pc")
public class gamingPc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    //Websiteurl of gaming Laptop
    @Column(name = "website_url")
    String website_url;

    //Surname of gaming Laptop
    @Column(name = "price")
    String price;

    //Other information about the Gaming Laptop
    @ManyToOne
    @JoinColumn(name="gaming_pc_id", nullable=false)
    gamingPcDetails details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public gamingPcDetails getDetails() {
        return details;
    }

    public void setDetails(gamingPcDetails details) {
        this.details = details;
    }


}
