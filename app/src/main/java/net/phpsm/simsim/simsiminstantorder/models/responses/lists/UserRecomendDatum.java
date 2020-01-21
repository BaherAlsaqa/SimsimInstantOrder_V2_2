
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRecomendDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type_recommend")
    @Expose
    private String typeRecommend;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("order_item_id")
    @Expose
    private Integer orderItemId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("provider_id")
    @Expose
    private Object providerId;
    @SerializedName("provider")
    @Expose
    private Object provider;
    @SerializedName("orderitem")
    @Expose
    private OrderRecomenditem orderitem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeRecommend() {
        return typeRecommend;
    }

    public void setTypeRecommend(String typeRecommend) {
        this.typeRecommend = typeRecommend;
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

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getProviderId() {
        return providerId;
    }

    public void setProviderId(Object providerId) {
        this.providerId = providerId;
    }

    public Object getProvider() {
        return provider;
    }

    public void setProvider(Object provider) {
        this.provider = provider;
    }

    public OrderRecomenditem getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(OrderRecomenditem orderitem) {
        this.orderitem = orderitem;
    }

}
