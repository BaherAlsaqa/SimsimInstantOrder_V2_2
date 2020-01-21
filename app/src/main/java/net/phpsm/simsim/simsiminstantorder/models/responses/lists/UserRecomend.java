
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRecomend {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("items")
    @Expose
    private UserRecomendItems items;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserRecomendItems getItems() {
        return items;
    }

    public void setItems(UserRecomendItems items) {
        this.items = items;
    }

}
