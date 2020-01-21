package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 09/01/2018.
 */

public class CarColors {
    @SerializedName("id")
    public int id;
    @SerializedName("color_name")
    public String color_name;
    @SerializedName("color_code")
    public String color_code;

    public CarColors(){}

    public CarColors(int id, String color_name, String color_code) {
        this.id = id;
        this.color_name = color_name;
        this.color_code = color_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }
}
