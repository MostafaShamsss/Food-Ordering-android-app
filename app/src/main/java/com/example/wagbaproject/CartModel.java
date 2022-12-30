package com.example.wagbaproject;

public class CartModel
{
    private String image;
    private String dishName, price, status;


    public CartModel(){}

    public CartModel(String image, String dishName, String price, String status)
    {
        this.image = image;
        this.dishName = dishName;
        this.price = price;
        this.status = status;
    }

    public String getStatus() { return status; }

    public String getImage() {
        return image;
    }

    public String getDishName() {
        return dishName;
    }

    public String getPrice() {
        return price;
    }

}
