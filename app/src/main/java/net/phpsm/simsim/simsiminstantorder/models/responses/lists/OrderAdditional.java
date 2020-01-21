
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;

import java.io.Serializable;

public class OrderAdditional implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("additional_price")
    @Expose
    private Double additionalPrice;
    @SerializedName("additional_amount")
    @Expose
    private String additionalAmount;
    @SerializedName("additional_total_price")
    @Expose
    private Double additionalTotalPrice;
    @SerializedName("order_item_id")
    @Expose
    private Integer orderItemId;
    @SerializedName("additional_id")
    @Expose
    private Integer additionalId;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("additional")
    @Expose
    private ProductAdditional additional;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(Double additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public String getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(String additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public Double getAdditionalTotalPrice() {
        return additionalTotalPrice;
    }

    public void setAdditionalTotalPrice(Double additionalTotalPrice) {
        this.additionalTotalPrice = additionalTotalPrice;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getAdditionalId() {
        return additionalId;
    }

    public void setAdditionalId(Integer additionalId) {
        this.additionalId = additionalId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public ProductAdditional getAdditional() {
        return additional;
    }

    public void setAdditional(ProductAdditional additional) {
        this.additional = additional;
    }

}
