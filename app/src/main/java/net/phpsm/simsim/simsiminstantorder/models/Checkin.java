package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 18/11/2017.
 */

public class Checkin {

    public String name;
    public String category;
    public int image;

    public Checkin(String name, String category, int image) {
        this.name = name;
        this.category = category;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
