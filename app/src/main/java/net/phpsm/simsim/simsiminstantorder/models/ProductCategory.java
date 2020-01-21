package net.phpsm.simsim.simsiminstantorder.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 16/11/2017.
 */

public class ProductCategory {

    @SerializedName("id")
    public int id;
    @SerializedName("product_category_name")
    public String product_category_name;
    @SerializedName("product_category_description")
    public String product_category_description;
    @SerializedName("product_category_icon")
    public String product_category_icon;
    @SerializedName("product_category_status")
    public String product_category_status;
    @SerializedName("url_image")
    public String url_image;

    public ProductCategory(int id, String product_category_name, String product_category_description, String product_category_icon, String product_category_status, String url_image) {
        this.id = id;
        this.product_category_name = product_category_name;
        this.product_category_description = product_category_description;
        this.product_category_icon = product_category_icon;
        this.product_category_status = product_category_status;
        this.url_image = url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_category_name() {
        return product_category_name;
    }

    public void setProduct_category_name(String product_category_name) {
        this.product_category_name = product_category_name;
    }

    public String getProduct_category_description() {
        return product_category_description;
    }

    public void setProduct_category_description(String product_category_description) {
        this.product_category_description = product_category_description;
    }

    public String getProduct_category_icon() {
        return product_category_icon;
    }

    public void setProduct_category_icon(String product_category_icon) {
        this.product_category_icon = product_category_icon;
    }

    public String getProduct_category_status() {
        return product_category_status;
    }

    public void setProduct_category_status(String product_category_status) {
        this.product_category_status = product_category_status;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
