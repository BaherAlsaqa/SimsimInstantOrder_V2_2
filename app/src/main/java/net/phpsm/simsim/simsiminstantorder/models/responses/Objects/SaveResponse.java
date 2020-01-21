package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 21/11/2017.
 */

public class SaveResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("affiliate_customer_id")
    @Expose
    private int affiliate_customer_id;
    @SerializedName("product_id")
    @Expose
    private int product_id;
    @SerializedName("order_item_id")
    @Expose
    private int order_item_id;
    @SerializedName("orderitem")
    @Expose
    private Orderitem orderitem;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public SaveResponse(){}

    public SaveResponse(int id, int customerId, int order_item_id, Orderitem orderitem) {
        this.id = id;
        this.customerId = customerId;
        this.order_item_id = order_item_id;
        this.orderitem = orderitem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Orderitem getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(Orderitem orderitem) {
        this.orderitem = orderitem;
    }

    public int getAffiliate_customer_id() {
        return affiliate_customer_id;
    }

    public void setAffiliate_customer_id(int affiliate_customer_id) {
        this.affiliate_customer_id = affiliate_customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
