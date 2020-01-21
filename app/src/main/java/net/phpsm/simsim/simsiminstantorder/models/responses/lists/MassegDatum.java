package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 1/21/2018.
 */

public class MassegDatum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("customer_read")
    @Expose
    private String customerRead;
    @SerializedName("customer_delete")
    @Expose
    private String customerDelete;
    @SerializedName("sent_at")
    @Expose
    private String sentAt;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("provider")
    @Expose
    private providerData provider;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerRead() {
        return customerRead;
    }

    public void setCustomerRead(String customerRead) {
        this.customerRead = customerRead;
    }

    public String getCustomerDelete() {
        return customerDelete;
    }

    public void setCustomerDelete(String customerDelete) {
        this.customerDelete = customerDelete;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public providerData getProvider() {
        return provider;
    }

    public void setProvider(providerData provider) {
        this.provider = provider;
    }

}
