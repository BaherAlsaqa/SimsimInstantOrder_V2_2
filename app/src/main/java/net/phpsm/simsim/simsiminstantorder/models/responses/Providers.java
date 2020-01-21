package net.phpsm.simsim.simsiminstantorder.models.responses;

import net.phpsm.simsim.simsiminstantorder.models.Branche;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by baher on 12/01/2018.
 */

public class Providers {
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
    @SerializedName("image")
    public String image;
    @SerializedName("cover_image")
    public String cover_image;
    @SerializedName("status")
    public String status;
    @SerializedName("typeid_id")
    public int typeid_id;
    @SerializedName("cusine_id")
    public int cusine_id;
    @SerializedName("call_type")
    public String call_type;
    @SerializedName("onoff")
    public Integer onoff;
    @SerializedName("url_image")
    public String url_image;

    @SerializedName("type")
    private TypeCategory type;
    @SerializedName("is_favorite")
    public  String is_favorite;
    @SerializedName("callcenter")
    private ArrayList<String> mobileCallCenterArrayList = new ArrayList<>();

   // @SerializedName("distance")
    public double distance;
    @SerializedName("branches")
    private ArrayList<Branche> BranchesList = new ArrayList<>();
    @SerializedName("cusine")
    public Cusine cusine;
    @SerializedName("delivery_status")
    private ArrayList<Delivery_Status> Delivery_Status = new ArrayList<>();
    @SerializedName("provider_category")
    private ArrayList<ProviderCategory> providerCategoryArrayList = new ArrayList<>();
    @SerializedName("typeid")
    private ProviderCategory providerCategory;
     @SerializedName("url_coverimage")
    private String url_coverimage;

    @SerializedName("url_coverimage1")
    private String url_coverimage1;

    @SerializedName("url_coverimage2")
    private String url_coverimage2;


    public Providers(){}

    public Providers(int id, String name, String description, String address, String mobile, String latitude, String longitude, String image, String cover_image, String status, int typeid_id, int cusine_id, String call_type, Integer onoff, String url_image, String url_coverimage, TypeCategory type, String is_favorite, ArrayList<String> mobileCallCenterArrayList, double distance, ArrayList<Branche> branchesList, Cusine cusine, ArrayList<net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status> delivery_Status, ArrayList<ProviderCategory> providerCategoryArrayList, ProviderCategory providerCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.cover_image = cover_image;
        this.status = status;
        this.typeid_id = typeid_id;
        this.cusine_id = cusine_id;
        this.call_type = call_type;
        this.onoff = onoff;
        this.url_image = url_image;
        this.url_coverimage = url_coverimage;
        this.type = type;
        this.is_favorite = is_favorite;
        this.mobileCallCenterArrayList = mobileCallCenterArrayList;
        this.distance = distance;
        BranchesList = branchesList;
        this.cusine = cusine;
        Delivery_Status = delivery_Status;
        this.providerCategoryArrayList = providerCategoryArrayList;
        this.providerCategory = providerCategory;
    }

    public Integer getOnoff() {
        return onoff;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
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

    public TypeCategory getType() {
        return type;
    }

    public void setType(TypeCategory type) {
        this.type = type;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public ArrayList<String> getMobileCallCenterArrayList() {
        return mobileCallCenterArrayList;
    }

    public void setMobileCallCenterArrayList(ArrayList<String> mobileCallCenterArrayList) {
        this.mobileCallCenterArrayList = mobileCallCenterArrayList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<Branche> getBranchesList() {
        return BranchesList;
    }

    public void setBranchesList(ArrayList<Branche> branchesList) {
        BranchesList = branchesList;
    }

    public Cusine getCusine() {
        return cusine;
    }

    public void setCusine(Cusine cusine) {
        this.cusine = cusine;
    }

    public ArrayList<net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status> getDelivery_Status() {
        return Delivery_Status;
    }

    public void setDelivery_Status(ArrayList<net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status> delivery_Status) {
        Delivery_Status = delivery_Status;
    }

    public ArrayList<ProviderCategory> getProviderCategoryArrayList() {
        return providerCategoryArrayList;
    }

    public void setProviderCategoryArrayList(ArrayList<ProviderCategory> providerCategoryArrayList) {
        this.providerCategoryArrayList = providerCategoryArrayList;
    }

    public ProviderCategory getProviderCategory() {
        return providerCategory;
    }

    public void setProviderCategory(ProviderCategory providerCategory) {
        this.providerCategory = providerCategory;
    }

    public String getUrl_coverimage1() {
        return url_coverimage1;
    }

    public void setUrl_coverimage1(String url_coverimage1) {
        this.url_coverimage1 = url_coverimage1;
    }

    public String getUrl_coverimage2() {
        return url_coverimage2;
    }

    public void setUrl_coverimage2(String url_coverimage2) {
        this.url_coverimage2 = url_coverimage2;
    }

    public static class Comparators {
        //public static final Comparator<Providers> NAME = (Student o1, Student o2) -> o1.name.compareTo(o2.name);
        public static final Comparator<Providers> distance = (Providers o1, Providers o2) -> Double.compare(o1.distance, o2.distance);
        //public static final Comparator<Providers> NAME_AND_AGE = (Student o1, Student o2) -> NAME.thenComparing(AGE).compare(o1, o2);
    }
}
