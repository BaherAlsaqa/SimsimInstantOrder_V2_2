package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 24/01/2018.
 */

public class OrderResponse {
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("total_price")
    @Expose
    private Double totalPrice;
    @SerializedName("count_additional")
    @Expose
    private Integer countAdditional;
    @SerializedName("price_additional_item")
    @Expose
    private Double priceAdditionalItem;
    @SerializedName("customer_track_time")
    @Expose
    private Integer customer_track_time;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("products_list")
    @Expose
    private String productsList;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("is_favorite")
    @Expose
    private String isFavorite;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCountAdditional() {
        return countAdditional;
    }

    public void setCountAdditional(Integer countAdditional) {
        this.countAdditional = countAdditional;
    }

    public Double getPriceAdditionalItem() {
        return priceAdditionalItem;
    }

    public void setPriceAdditionalItem(Double priceAdditionalItem) {
        this.priceAdditionalItem = priceAdditionalItem;
    }

    public Integer getCustomer_track_time() {
        return customer_track_time;
    }

    public void setCustomer_track_time(Integer customer_track_time) {
        this.customer_track_time = customer_track_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductsList() {
        return productsList;
    }

    public void setProductsList(String productsList) {
        this.productsList = productsList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }
}
