package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 17/01/2018.
 */

public class Orderitem {
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
    private String deletedAt;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("customer_image")
    @Expose
    private String customerImage;
    @SerializedName("save")
    @Expose
    private Integer save;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("is_saved")
    @Expose
    private String is_saved;
    @SerializedName("is_react")
    @Expose
    private String is_react;
    @SerializedName("product")
    @Expose
    private Products products;

    @SerializedName("order")
    @Expose
    private Order order;

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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
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

    public String getIs_saved() {
        return is_saved;
    }

    public void setIs_saved(String is_saved) {
        this.is_saved = is_saved;
    }

    public String getIs_react() {
        return is_react;
    }

    public void setIs_react(String is_react) {
        this.is_react = is_react;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
