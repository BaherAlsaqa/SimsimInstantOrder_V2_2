package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 19/11/2017.
 */

public class Addfriends {

    public String name;
    public int image;

    public Addfriends(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
