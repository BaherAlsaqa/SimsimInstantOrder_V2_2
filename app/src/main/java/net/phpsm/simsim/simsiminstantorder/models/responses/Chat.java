package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;

/**
 * Created by baher on 29/03/2018.
 */

public class Chat {
    @SerializedName("id")
    public int id;
    @SerializedName("provider_id")
    public Integer provider_id;
    @SerializedName("customer_id")
    public Integer customer_id;
    @SerializedName("content")
    public String content;
    @SerializedName("customer_read")
    public String customer_read;
    @SerializedName("customer_delete")
    public String customer_delete;
    @SerializedName("sent_at")
    public String sent_at;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("deleted_at")
    public String deleted_at;
    @SerializedName("sender")
    public int sender;
    @SerializedName("provider")
    public Providers providers;



    public Chat(){}

    public Chat(int id, Integer provider_id, Integer customer_id, String content, String customer_read, String customer_delete, String sent_at, String created_at, String updated_at, String deleted_at, int sender, Providers providers) {
        this.id = id;
        this.provider_id = provider_id;
        this.customer_id = customer_id;
        this.content = content;
        this.customer_read = customer_read;
        this.customer_delete = customer_delete;
        this.sent_at = sent_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.sender = sender;
        this.providers = providers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Integer provider_id) {
        this.provider_id = provider_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomer_read() {
        return customer_read;
    }

    public void setCustomer_read(String customer_read) {
        this.customer_read = customer_read;
    }

    public String getCustomer_delete() {
        return customer_delete;
    }

    public void setCustomer_delete(String customer_delete) {
        this.customer_delete = customer_delete;
    }

    public String getSent_at() {
        return sent_at;
    }

    public void setSent_at(String sent_at) {
        this.sent_at = sent_at;
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

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }
}
