package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 18/11/2017.
 */

public class Restaurant {

    public int image;
    public String name;
    public Double price;

    public Restaurant(int image, String name, Double price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
