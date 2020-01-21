package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import net.phpsm.simsim.simsiminstantorder.models.responses.Car;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 09/01/2018.
 */

public class CustomerObject {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("followers")
    @Expose
    public int followers;
    @SerializedName("following")
    @Expose
    public int following;
    @SerializedName("is_follow")
    @Expose
    private Boolean is_follow;
    @SerializedName("url_image")
    @Expose
    private String url_image;
    @SerializedName("car")
    @Expose
    public Car car;

    @SerializedName("privac")
    public int privac;

    public int getPrivac() {
        return privac;
    }

    public void setPrivac(int privac) {
        this.privac = privac;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("address")
    @Expose
    public String address;


    public CustomerObject(){}

    public CustomerObject(int id, String image, String latitude, String longitude, String createdAt, String updatedAt, String deletedAt, String mobile, String email, String name, int followers, int following, Boolean is_follow, Car car,String address) {
        this.id = id;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.mobile = mobile;
        this.email = email;
        this.name = name;
        this.followers = followers;
        this.following = following;
        this.is_follow = is_follow;
        this.car = car;
        this.address=address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public Boolean getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(Boolean is_follow) {
        this.is_follow = is_follow;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getAddress() {
        return address;
    }

}
