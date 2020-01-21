package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 21/11/2017.
 */

public class Community {

    public int image;
    public String name;
    public String location;

    public Community(int image, String name, String location) {
        this.image = image;
        this.name = name;
        this.location = location;
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
        return location;
    }

    public void setTime(String time) {
        this.location = time;
    }
}
