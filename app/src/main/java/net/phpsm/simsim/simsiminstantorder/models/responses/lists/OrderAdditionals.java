package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 1/17/2018.
 */

public class OrderAdditionals {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;

    @SerializedName("additional_price")
    @Expose
    private Double additional_price;

    @SerializedName("additional_amount")
    @Expose
    private String additional_amount;

    @SerializedName("additional_total_price")
    @Expose
    private Double additional_total_price;

    @SerializedName("order_item_id")
    @Expose
    private int order_item_id;


    @SerializedName("additional_id")
    @Expose
    private int additional_id;


    @SerializedName("order_id")
    @Expose
    private int order_id;


    @SerializedName("additional")
    @Expose
    private Additional additionals;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Double getAdditional_price() {
        return additional_price;
    }

    public void setAdditional_price(Double additional_price) {
        this.additional_price = additional_price;
    }

    public String getAdditional_amount() {
        return additional_amount;
    }

    public void setAdditional_amount(String additional_amount) {
        this.additional_amount = additional_amount;
    }

    public Double getAdditional_total_price() {
        return additional_total_price;
    }

    public void setAdditional_total_price(Double additional_total_price) {
        this.additional_total_price = additional_total_price;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getAdditional_id() {
        return additional_id;
    }

    public void setAdditional_id(int additional_id) {
        this.additional_id = additional_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Additional getAdditionals() {
        return additionals;
    }

    public void setAdditionals(Additional additionals) {
        this.additionals = additionals;
    }
}
