package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 10/01/2018.
 */

public class PProfileObject {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public CustomerObject customerObject;

    public PProfileObject(){}

    public PProfileObject(String status, CustomerObject customerObject) {
        this.status = status;
        this.customerObject = customerObject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerObject getCustomerObject() {
        return customerObject;
    }

    public void setCustomerObject(CustomerObject customerObject) {
        this.customerObject = customerObject;
    }
}
