package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 18/11/2017.
 */

public class NotificationClass {

    public int image, fromimage;
    public String name;
    public String datetime;

    public NotificationClass(int image, int fromimage, String name, String datetime) {
        this.image = image;
        this.fromimage = fromimage;
        this.name = name;
        this.datetime = datetime;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getFromimage() {
        return fromimage;
    }

    public void setFromimage(int fromimage) {
        this.fromimage = fromimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
