package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 11/01/2018.
 */

public class Avatar {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("url_image")
    @Expose
    public String url_image;

    public Avatar(String status, String url_image) {
        this.status = status;
        this.url_image = url_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
