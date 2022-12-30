package com.example.wagbaproject;

public class OrderHistoryModel
{
    private String dishName, price, status;

    public OrderHistoryModel(String dishName, String price, String status, String paidOrNot) {
        this.dishName = dishName;
        this.price = price;
        this.status = status;
    }


    public String getDishName() {
        return dishName;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
