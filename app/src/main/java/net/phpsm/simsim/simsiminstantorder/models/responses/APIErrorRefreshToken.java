package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 03/01/2018.
 */

public class APIErrorRefreshToken {
    @SerializedName("message")
    private String message;

    public APIErrorRefreshToken() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
