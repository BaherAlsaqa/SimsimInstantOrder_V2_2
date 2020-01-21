
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("items")
    @Expose
    private UsersItems items;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UsersItems getItems() {
        return items;
    }

    public void setItems(UsersItems items) {
        this.items = items;
    }

}
