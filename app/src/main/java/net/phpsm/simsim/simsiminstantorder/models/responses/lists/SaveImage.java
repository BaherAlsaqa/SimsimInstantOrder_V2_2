package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 1/18/2018.
 */

public class SaveImage {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("url_customer_image")
    @Expose
    private String url_customer_image;

    @SerializedName("customer_file_a")
    @Expose
    private String customer_file_a;

    @SerializedName("customer_file_b")
    @Expose
    private String customer_file_b;

    @SerializedName("customer_file_c")
    @Expose
    private String customer_file_c;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUrl_customer_image() {
        return url_customer_image;
    }

    public void setUrl_customer_image(String url_customer_image) {
        this.url_customer_image = url_customer_image;
    }

    public String getCustomer_file_a() {
        return customer_file_a;
    }

    public void setCustomer_file_a(String customer_file_a) {
        this.customer_file_a = customer_file_a;
    }

    public String getCustomer_file_b() {
        return customer_file_b;
    }

    public void setCustomer_file_b(String customer_file_b) {
        this.customer_file_b = customer_file_b;
    }

    public String getCustomer_file_c() {
        return customer_file_c;
    }

    public void setCustomer_file_c(String customer_file_c) {
        this.customer_file_c = customer_file_c;
    }
}
