package com.github.beetrox.packapp;

/**
 * Created by Owner on 09/03/2018.
 */

public class ListItem {

    private String name;
    private String status;
    private String category;
    private int amount;

    public ListItem(String name, String category) {
        this.name = name;
        this.status = "red";
        this.category = category;
        this.amount = 0;
    }

    public ListItem(){}

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
