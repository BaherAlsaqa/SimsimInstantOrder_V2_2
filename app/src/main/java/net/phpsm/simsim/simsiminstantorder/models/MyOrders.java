package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 16/11/2017.
 */

public class MyOrders {

    public int image;
    public String title;
    public String datetime;
    public String orderstatus;
    public Double totalprice;

    public MyOrders(int image, String title, String datetime, String orderstatus, Double totalprice) {
        this.image = image;
        this.title = title;
        this.datetime = datetime;
        this.orderstatus = orderstatus;
        this.totalprice = totalprice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Double getPrice() {
        return totalprice;
    }

    public void setPrice(Double price) {
        this.totalprice = price;
    }
}
