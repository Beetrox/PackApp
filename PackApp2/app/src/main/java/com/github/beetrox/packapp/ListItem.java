package com.github.beetrox.packapp;

/**
 * Created by Owner on 09/03/2018.
 */

public class ListItem {

    private String name;
    private String status;
    private int amount;

    public ListItem(String name) {
        this.name = name;
        this.status = "red";
        this.amount = 0;
    }

    public ListItem(){}

    public String getName() {
        return name;
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
