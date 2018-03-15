package com.github.beetrox.packapp;

/**
 * Created by Owner on 09/03/2018.
 */

public class ListItem {

    private String name;
    private String category;
    private String status;
    private int amount;

    public ListItem(String name, String category) {
        this.name = name;
        this.category = category;
        this.status = "red";
        this.amount = 0;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int Amount) {
        this.amount = amount;
    }

}
