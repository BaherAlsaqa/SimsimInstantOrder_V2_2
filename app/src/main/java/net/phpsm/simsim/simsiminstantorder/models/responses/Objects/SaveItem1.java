package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 26/01/2018.
 */

public class SaveItem1 {
    @SerializedName("status")
    public String status;

    public SaveItem1(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}