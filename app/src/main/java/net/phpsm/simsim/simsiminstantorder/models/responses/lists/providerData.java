package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HP on 1/16/2018.
 */

public class providerData implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("mobile")
    @Expose
    private String mobile;


    @SerializedName("latitude")
    @Expose
    private String latitude;


    @SerializedName("longitude")
    @Expose
    private String longitude;


    @SerializedName("image")
    @Expose
    private String image;



    @SerializedName("cover_image")
    @Expose
    private String cover_image;



    @SerializedName("status")
    @Expose
    private String status;



    @SerializedName("created_at")
    @Expose
    private String created_at;



    @SerializedName("updated_at")
    @Expose
    private String updated_at;


    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;


    @SerializedName("typeid_id")
    @Expose
    private int typeid_id;


    @SerializedName("cusine_id")
    @Expose
    private int cusine_id;


    @SerializedName("call_type")
    @Expose
    private String call_type;


    @SerializedName("url_image")
    @Expose
    private String url_image;


    @SerializedName("url_coverimage")
    @Expose
    private String url_coverimage;

    @SerializedName("is_favorite")
    @Expose
    private Boolean is_favorite;

    public Boolean getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getTypeid_id() {
        return typeid_id;
    }

    public void setTypeid_id(int typeid_id) {
        this.typeid_id = typeid_id;
    }

    public int getCusine_id() {
        return cusine_id;
    }

    public void setCusine_id(int cusine_id) {
        this.cusine_id = cusine_id;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getUrl_coverimage() {
        return url_coverimage;
    }

    public void setUrl_coverimage(String url_coverimage) {
        this.url_coverimage = url_coverimage;
    }
}
