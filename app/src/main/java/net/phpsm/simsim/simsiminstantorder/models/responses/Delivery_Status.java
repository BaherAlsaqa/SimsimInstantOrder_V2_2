package net.phpsm.simsim.simsiminstantorder.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class Delivery_Status {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("icon")
    public String icon;
    @SerializedName("available")
    public String available;
    @SerializedName("url_icon")
    public String url_icon;
    @SerializedName("pivot")
    public Pivot pivot;

    public Delivery_Status(){
        super();
    }

    public Delivery_Status(int id, String name, String icon, String available, String url_icon, Pivot pivot) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.available = available;
        this.url_icon = url_icon;
        this.pivot = pivot;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    private class Pivot {
        @SerializedName("provider_id")
        public int provider_id;
        @SerializedName("delivery_id")
        public int delivery_id;

        public Pivot(int provider_id, int delivery_id) {
            this.provider_id = provider_id;
            this.delivery_id = delivery_id;
        }

        public int getProvider_id() {
            return provider_id;
        }

        public void setProvider_id(int provider_id) {
            this.provider_id = provider_id;
        }

        public int getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(int delivery_id) {
            this.delivery_id = delivery_id;
        }
    }
}
