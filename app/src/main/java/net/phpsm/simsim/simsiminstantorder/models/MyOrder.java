package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 20/11/2017.
 */

public class MyOrder {

    public String details;
    public Double price;
    public String user_num;

    public MyOrder(String details, Double price, String user_num) {
        this.details = details;
        this.price = price;
        this.user_num = user_num;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }
}
