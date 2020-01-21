package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 11/26/2017.
 */

public class LoginResponse {
    @SerializedName("token_type")
    private String token_type;
    @SerializedName("expires_in")
    private String expires_in;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("is_validate")
    private Integer is_validate;


    public LoginResponse(String token_type, String expires_in, String access_token, String refresh_token, Integer is_validate) {
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.is_validate = is_validate;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getIs_validate() {
        return is_validate;
    }

    public void setIs_validate(Integer is_validate) {
        this.is_validate = is_validate;
    }
}