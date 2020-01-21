
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;

public class OrderRecomenditem {

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
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("customer_image")
    @Expose
    private Object customerImage;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

}
