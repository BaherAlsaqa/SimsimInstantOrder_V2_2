package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;

/**
 * Created by HP on 1/16/2018.
 */

public class PurchaseorderItems {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("product_id")
    @Expose
    private String product_id;


    @SerializedName("price_product")
    @Expose
    private Double price_product;


    @SerializedName("amount")
    @Expose
    private Integer amount;


    @SerializedName("created_at")
    @Expose
    private String created_at;


    @SerializedName("updated_at")
    @Expose
    private String updated_at;


    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;


    @SerializedName("order_id")
    @Expose
    private Integer order_id;


    @SerializedName("customer_image")
    @Expose
    private String customer_image;

    @SerializedName("customer_video")
    @Expose
    private  String customer_video;

    @SerializedName("save")
    @Expose
    private Integer save;

    @SerializedName("customer_id")
    @Expose
    private Integer customer_id;

    @SerializedName("purchase_points")
    @Expose
    private Integer purchase_points;

    @SerializedName("review_points")
    @Expose
    private Integer review_points;

    @SerializedName("count_react_count")
    @Expose
    private Integer count_react_count;

    @SerializedName("product")
    @Expose
    private Products product;
    @SerializedName("recommend")
    @Expose
    private recomendData recommend;

    @SerializedName("customer_file_a")
    @Expose
    private String customer_file_a;

    @SerializedName("customer_file_b")
    @Expose
    private  String customer_file_b;

    @SerializedName("customer_file_c")
    @Expose
    private  String customer_file_c;

    @SerializedName("customer_file_a_url")
    @Expose
    private String customer_file_a_url;

    @SerializedName("customer_file_b_url")
    @Expose
    private  String customer_file_b_url;

    @SerializedName("customer_file_c_url")
    @Expose
    private  String customer_file_c_url;
    @SerializedName("total_price_and_additional")
    @Expose
    private Double total_price_and_additional;
    @SerializedName("total_price_item")
    @Expose
    private Double totalPriceItem;

    public Double getTotalPriceItem() {
        return totalPriceItem;
    }

    public void setTotalPriceItem(Double totalPriceItem) {
        this.totalPriceItem = totalPriceItem;
    }

    public Double getTotal_price_and_additional() {
        return total_price_and_additional;
    }

    public void setTotal_price_and_additional(Double total_price_and_additional) {
        this.total_price_and_additional = total_price_and_additional;
    }

    public Object getIs_react_rel() {
        return is_react_rel;
    }

    public void setIs_react_rel(Object is_react_rel) {
        this.is_react_rel = is_react_rel;
    }

    @SerializedName("is_react_rel")
   @Expose
   private Object is_react_rel;




    public String getCustomer_video() {
        return customer_video;
    }

    public void setCustomer_video(String customer_video) {
        this.customer_video = customer_video;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Double getPrice_product() {
        return price_product;
    }

    public void setPrice_product(Double price_product) {
        this.price_product = price_product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public recomendData getRecommend() {
        return recommend;
    }

    public void setRecommend(recomendData recommend) {
        this.recommend = recommend;
    }

    public Integer getPurchase_points() {
        return purchase_points;
    }

    public void setPurchase_points(Integer purchase_points) {
        this.purchase_points = purchase_points;
    }

    public Integer getReview_points() {
        return review_points;
    }

    public void setReview_points(Integer review_points) {
        this.review_points = review_points;
    }

    public Integer getCount_react_count() {
        return count_react_count;
    }

    public void setCount_react_count(Integer count_react_count) {
        this.count_react_count = count_react_count;
    }

    public String getCustomer_file_a() {
        return customer_file_a;
    }

    public void setCustomer_file_a(String customer_file_a) {
        this.customer_file_a = customer_file_a;
    }

    public String getCustomer_file_b() {
        return customer_file_b;
    }

    public void setCustomer_file_b(String customer_file_b) {
        this.customer_file_b = customer_file_b;
    }

    public String getCustomer_file_c() {
        return customer_file_c;
    }

    public void setCustomer_file_c(String customer_file_c) {
        this.customer_file_c = customer_file_c;
    }

    public String getCustomer_file_a_url() {
        return customer_file_a_url;
    }

    public void setCustomer_file_a_url(String customer_file_a_url) {
        this.customer_file_a_url = customer_file_a_url;
    }

    public String getCustomer_file_b_url() {
        return customer_file_b_url;
    }

    public void setCustomer_file_b_url(String customer_file_b_url) {
        this.customer_file_b_url = customer_file_b_url;
    }

    public String getCustomer_file_c_url() {
        return customer_file_c_url;
    }

    public void setCustomer_file_c_url(String customer_file_c_url) {
        this.customer_file_c_url = customer_file_c_url;
    }
}

