package net.phpsm.simsim.simsiminstantorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 18/11/2017.
 */

public class ProductAdditional {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("choice_1")
    @Expose
    private String choice_1;

    @SerializedName("choice_1_price")
    @Expose
    private Double choice_1_price;

    @SerializedName("choice_2")
    @Expose
    private String choice_2;

    @SerializedName("choice_2_price")
    @Expose
    private Double choice_2_price;

    @SerializedName("choice_3")
    @Expose
    private String choice_3;

    @SerializedName("choice_3_price")
    @Expose
    private Double choice_3_price;

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("url_image")
    @Expose
    private String urlImage;
    // is amount
    public int selected;

    public ProductAdditional(Integer id, String name, String choice_1, Double choice_1_price, String choice_2, Double choice_2_price, String choice_3, Double choice_3_price, Integer productId, Integer providerId, String image, String urlImage, int selected) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.choice_1 = choice_1;
        this.choice_1_price = choice_1_price;
        this.choice_2 = choice_2;
        this.choice_2_price = choice_2_price;
        this.choice_3 = choice_3;
        this.choice_3_price = choice_3_price;
        this.productId = productId;
        this.providerId = providerId;
        this.image = image;
        this.urlImage = urlImage;
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getChoice_1() {
        return choice_1;
    }

    public void setChoice_1(String choice_1) {
        this.choice_1 = choice_1;
    }

    public Double getChoice_1_price() {
        return choice_1_price;
    }

    public void setChoice_1_price(Double choice_1_price) {
        this.choice_1_price = choice_1_price;
    }

    public String getChoice_2() {
        return choice_2;
    }

    public void setChoice_2(String choice_2) {
        this.choice_2 = choice_2;
    }

    public Double getChoice_2_price() {
        return choice_2_price;
    }

    public void setChoice_2_price(Double choice_2_price) {
        this.choice_2_price = choice_2_price;
    }

    public String getChoice_3() {
        return choice_3;
    }

    public void setChoice_3(String choice_3) {
        this.choice_3 = choice_3;
    }

    public Double getChoice_3_price() {
        return choice_3_price;
    }

    public void setChoice_3_price(Double choice_3_price) {
        this.choice_3_price = choice_3_price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
