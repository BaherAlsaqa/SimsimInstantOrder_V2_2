package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 01/11/2017.
 */

public class MyPurchase {

    public int image;
    public String name;
    public String time;

    public MyPurchase(int image, String name, String time) {
        this.image = image;
        this.name = name;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
