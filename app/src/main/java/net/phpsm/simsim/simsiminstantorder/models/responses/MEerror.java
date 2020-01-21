package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MSI on 3/4/2018.
 */

public  class MEerror {

    @SerializedName("code")
    String code;

    @SerializedName("error_message")
    String error_message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
