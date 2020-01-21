package net.phpsm.simsim.simsiminstantorder.models;

import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 24/01/2018.
 */

public class OrderLoader {
    @SerializedName("products")
    @Expose
    private ArrayList<Product> products = null;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("delivery_id")
    @Expose
    private int deliveryId;

    @SerializedName("transaction_type_id")
    @Expose
    private int transaction_type_id;
    @SerializedName("affiliate_customer_id")
    @Expose
    private int affiliate_customer_id;
    @SerializedName("order_item_id")
    @Expose
    private int order_item_id;
    @SerializedName("product_id")
    @Expose
    private int product_id;

    @SerializedName("checkin_arrival")
    @Expose
    private int checkin_arrival;

    public OrderLoader(ArrayList<Product> products, String paymentMethod, Integer providerId, Integer branchId, int deliveryId, int transaction_type_id, int affiliate_customer_id, int order_item_id, int product_id, int checkin_arrival) {
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.providerId = providerId;
        this.branchId = branchId;
        this.deliveryId = deliveryId;
        this.transaction_type_id = transaction_type_id;
        this.affiliate_customer_id = affiliate_customer_id;
        this.order_item_id = order_item_id;
        this.product_id = product_id;
        this.checkin_arrival = checkin_arrival;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public int getAffiliate_customer_id() {
        return affiliate_customer_id;
    }

    public void setAffiliate_customer_id(int affiliate_customer_id) {
        this.affiliate_customer_id = affiliate_customer_id;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCheckin_arrival() {
        return checkin_arrival;
    }

    public void setCheckin_arrival(int checkin_arrival) {
        this.checkin_arrival = checkin_arrival;
    }
}
