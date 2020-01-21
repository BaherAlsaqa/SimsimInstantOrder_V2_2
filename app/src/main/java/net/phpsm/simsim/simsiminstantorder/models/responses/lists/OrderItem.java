
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;

import java.io.Serializable;

public class OrderItem implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("price_product")
    @Expose
    private Double priceProduct;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("total_price_item")
    @Expose
    private Double totalPriceItem;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("customer_image")
    @Expose
    private Object customerImage;
    @SerializedName("save")
    @Expose
    private Integer save;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("total_price_and_additional")
    @Expose
    private Double total_price_and_additional;
    @SerializedName("product")
    @Expose
    private Products products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getTotalPriceItem() {
        return totalPriceItem;
    }

    public void setTotalPriceItem(Double totalPriceItem) {
        this.totalPriceItem = totalPriceItem;
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

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Object getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(Object customerImage) {
        this.customerImage = customerImage;
    }

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getTotal_price_and_additional() {
        return total_price_and_additional;
    }

    public void setTotal_price_and_additional(Double total_price_and_additional) {
        this.total_price_and_additional = total_price_and_additional;
    }

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products products) {
        this.products = products;
    }

}
