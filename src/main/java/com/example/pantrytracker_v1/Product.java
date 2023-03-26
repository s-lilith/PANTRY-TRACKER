package com.example.pantrytracker_v1;

import java.io.Serializable;

public class Product implements Serializable {
    private String id, name, description,barcode, image;

    public Product(String id, String name, String description, String barcode, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.barcode= barcode;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getImage() {
        return image;
    }
}