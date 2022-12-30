package com.example.wagbaproject;

import java.io.Serializable;
import java.util.ArrayList;

public class RestDetailsModel
{

    private String image;
    private String dishName, components, price, availability, key;


    public RestDetailsModel(){}

    public RestDetailsModel(String image, String dishName, String components, String price, String availability) {
        this.image = image;
        this.dishName = dishName;
        this.components = components;
        this.price = price;
        this.availability = availability;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public String getDishName() {
        return dishName;
    }

    public String getComponents() {
        return components;
    }

    public String getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }
}
