package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 23/01/2018.
 */

class Stockout {
    @SerializedName("id")
    public int id;
    @SerializedName("provider_id")
    public int provider_id;
    @SerializedName("branch_id")
    public int branch_id;
    @SerializedName("product_id")
    public int product_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("deleted_at")
    public String deleted_at;

    public Stockout(int id, int provider_id, int branch_id, int product_id, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.provider_id = provider_id;
        this.branch_id = branch_id;
        this.product_id = product_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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
}
