package net.phpsm.simsim.simsiminstantorder.models;

/**
 * Created by baher on 16/11/2017.
 */

public class Wallet {

    public String datetime;
    public String pointNum;
    public String title;
    public int image;

    public Wallet(String datetime, String pointNum, String title, int image) {
        this.datetime = datetime;
        this.pointNum = pointNum;
        this.title = title;
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPointNum() {
        return pointNum;
    }

    public void setPointNum(String pointNum) {
        this.pointNum = pointNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
