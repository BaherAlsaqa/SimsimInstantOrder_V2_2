package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Additional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.PurchaseorderItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baher on 13/01/2018.
 */

public class Order_ {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("products_list")
    @Expose
    private String productsList;
    @SerializedName("price_additional_item")
    @Expose
    private Double priceAdditionalItem;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("total_price")
    @Expose
    private Double totalPrice;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("count_additional")
    @Expose
    private Integer countAdditional;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("customer_track_time")
    @Expose
    private String customerTrackTime;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("provider")
    @Expose
    private Providers providers;

    @SerializedName("order_items")
    @Expose
    private ArrayList<PurchaseorderItems> order_items = null;

    @SerializedName("order_additionals")
    @Expose
    private ArrayList<OrderAdditional> productAdditionalArrayList = null;

    public ArrayList<OrderAdditional> getProductAdditionalArrayList() {
        return productAdditionalArrayList;
    }

    public void setProductAdditionalArrayList(ArrayList<OrderAdditional> productAdditionalArrayList) {
        this.productAdditionalArrayList = productAdditionalArrayList;
    }

    public ArrayList<PurchaseorderItems> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(ArrayList<PurchaseorderItems> order_items) {
        this.order_items = order_items;
    }

    public Order_(Integer id, String status, String productsList, Double priceAdditionalItem, Double price, Double totalPrice, String paymentMethod, Integer countAdditional, String orderCode, String createdAt, String updatedAt, String deletedAt, Integer providerId, Integer customerId, String deliveryTime, String customerTrackTime, Integer branchId, Providers providers) {
        this.id = id;
        this.status = status;
        this.productsList = productsList;
        this.priceAdditionalItem = priceAdditionalItem;
        this.price = price;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.countAdditional = countAdditional;
        this.orderCode = orderCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.providerId = providerId;
        this.customerId = customerId;
        this.deliveryTime = deliveryTime;
        this.customerTrackTime = customerTrackTime;
        this.branchId = branchId;
        this.providers = providers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getPriceAdditionalItem() {
        return priceAdditionalItem;
    }

    public void setPriceAdditionalItem(Double priceAdditionalItem) {
        this.priceAdditionalItem = priceAdditionalItem;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCountAdditional() {
        return countAdditional;
    }

    public void setCountAdditional(Integer countAdditional) {
        this.countAdditional = countAdditional;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCustomerTrackTime() {
        return customerTrackTime;
    }

    public void setCustomerTrackTime(String customerTrackTime) {
        this.customerTrackTime = customerTrackTime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }
}
