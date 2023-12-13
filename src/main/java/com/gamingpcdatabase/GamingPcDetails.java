package com.gamingpcdatabase;

import jakarta.persistence.*;

import java.util.List;

/**
 * Class modeling of a Gaming PC Details
 */
@Entity
@Table(name="gaming_pc_details")
public class GamingPcDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Name of the gaming PC
    @Column(name = "name")
    private String name;

    // Brand of the gaming PC
    @Column(name = "brand")
    private String brand;

    // Model of the gaming PC
    @Column(name = "model")
    private String model;

    // Description of the gaming PC
    @Column(name = "description")
    private String description;

    // One-to-many relationship with gamingPc
    @OneToMany(mappedBy = "details", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GamingPc> gamingPcs;

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GamingPc> getGamingPcs() {
        return gamingPcs;
    }

    public void setGamingPcs(List<GamingPc> gamingPcs) {
        this.gamingPcs = gamingPcs;
    }
}
