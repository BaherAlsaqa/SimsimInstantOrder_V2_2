package net.phpsm.simsim.simsiminstantorder.models;

import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;

/**
 * Created by baher on 18/11/2017.
 */

public class Branche {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("address")
    public String address;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("provider_id")
    public int provider_id;
    @SerializedName("created_by_id")
    public int created_by_id;
    @SerializedName("provider")
    public Providers providers;

    @SerializedName("distance")
    public double distance;

    @SerializedName("url_branchcoverimage")
    public  String url_branchcoverimage;

    @SerializedName("url_branchcoverimage1")
    public  String url_branchcoverimage1;

    @SerializedName("url_branchcoverimage2")
    public  String url_branchcoverimage2;

    @SerializedName("onoff")
    public int onoff;


    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public String getUrl_branchcoverimage() {
        return url_branchcoverimage;
    }

    public void setUrl_branchcoverimage(String url_branchcoverimage) {
        this.url_branchcoverimage = url_branchcoverimage;
    }

    public String getUrl_branchcoverimage1() {
        return url_branchcoverimage1;
    }

    public void setUrl_branchcoverimage1(String url_branchcoverimage1) {
        this.url_branchcoverimage1 = url_branchcoverimage1;
    }

    public String getUrl_branchcoverimage2() {
        return url_branchcoverimage2;
    }

    public void setUrl_branchcoverimage2(String url_branchcoverimage2) {
        this.url_branchcoverimage2 = url_branchcoverimage2;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Branche(int id, String name, String description, String address, String mobile, String latitude, String longitude, int provider_id, int created_by_id, Providers providers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provider_id = provider_id;
        this.created_by_id = created_by_id;
        this.providers = providers;
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

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getCreated_by_id() {
        return created_by_id;
    }

    public void setCreated_by_id(int created_by_id) {
        this.created_by_id = created_by_id;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }
}
