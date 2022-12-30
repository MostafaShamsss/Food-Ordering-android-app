package com.example.wagbaproject;

import java.io.Serializable;
import java.util.ArrayList;

public class RestAvailModel implements Serializable
{
    private String image;
    private String restName, kindOfFood;
    private int restID;
    private ArrayList<RestDetailsModel> dishList;

    public RestAvailModel(String image, String restName, String kindOfFood, int restID)
    {
        this.image = image;
        this.restName = restName;
        this.kindOfFood = kindOfFood;
        this.restID = restID;
    }


    public RestAvailModel(String image, String restName, String kindOfFood, ArrayList<RestDetailsModel> dishList)
    {
        this.image = image;
        this.restName = restName;
        this.kindOfFood = kindOfFood;
        this.dishList = dishList;
    }



    public int getRestID() {
        return restID;
    }

    public String getImage() {
        return image;
    }

    public String getRestName() {
        return restName;
    }

    public String getKindOfFood() {
        return kindOfFood;
    }

    public ArrayList<RestDetailsModel> getDishList() {
        return dishList;
    }
}
