package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 10/01/2018.
 */

public class UpdateCustomer {
    @SerializedName("status")
    public String status;

    public UpdateCustomer(){}

    public UpdateCustomer(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
