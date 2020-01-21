package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by baher on 06/01/2018.
 */

public class CheckMobile {
    @SerializedName("status")
    String status;
    @SerializedName("error")
    List<MEerror> error;

    public CheckMobile(String status,  List<MEerror> error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  List<MEerror> getError() {
        return error;
    }

    public void setError( List<MEerror> error) {
        this.error = error;
    }
}
