package net.phpsm.simsim.simsiminstantorder.models;

import net.phpsm.simsim.simsiminstantorder.models.responses.Order_;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 18/11/2017.
 */

public class FavoritsOrders {

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
    private String deletedAt;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("order")
    @Expose
    private Order_ order;

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

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Object getProviderId() {
        return providerId;
    }

    @Override
    public String toString() {
        return "FavoritsOrders{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                ", providerId=" + providerId +
                ", order=" + order +
                '}';
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Order_ getOrder() {
        return order;
    }

    public void setOrder(Order_ order) {
        this.order = order;
    }


}
