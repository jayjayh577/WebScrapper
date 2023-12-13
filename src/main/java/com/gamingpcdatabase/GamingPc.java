package com.gamingpcdatabase;

import jakarta.persistence.*;

/**
 * Class representing a Gaming PC
 */
@Entity
@Table(name="gaming_pc")
public class GamingPc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Website URL of the gaming PC
    @Column(name = "website_url")
    private String websiteUrl;

    // Price of the gaming PC
    @Column(name = "price" )
    private String price; // Assuming price is a numeric value

    // Image URL of the gaming PC
    @Column(name = "image_url")
    private String imageUrl;

    // Relationship with gamingPcDetails
    @ManyToOne
    @JoinColumn(name = "gaming_pc_details_id", referencedColumnName = "id")
    private GamingPcDetails details;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String
    getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GamingPcDetails getDetails() {
        return details;
    }

    public void setDetails(GamingPcDetails details) {
        this.details = details;
    }
}
