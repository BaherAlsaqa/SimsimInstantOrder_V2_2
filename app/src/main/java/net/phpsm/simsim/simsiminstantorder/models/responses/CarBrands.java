package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 07/01/2018.
 */

public class CarBrands {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;

    @SerializedName("url_image")
    public String url_img;

    @SerializedName("image")
    public String img;


    public CarBrands(){}

    public CarBrands(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
