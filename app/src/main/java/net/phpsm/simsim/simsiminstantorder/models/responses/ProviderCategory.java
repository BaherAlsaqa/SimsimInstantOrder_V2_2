package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class ProviderCategory {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("image")
    public String image;
    @SerializedName("url_image")
    public String url_image;

    public ProviderCategory(){}

    public ProviderCategory(int id, String name, String description, String image, String url_image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.url_image = url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
