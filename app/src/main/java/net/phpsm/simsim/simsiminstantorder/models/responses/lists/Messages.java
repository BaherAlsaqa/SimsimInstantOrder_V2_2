
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("items")
    @Expose
    private MessageItem items ;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MessageItem getItems() {
        return items;
    }

    public void setItems(MessageItem items) {
        this.items = items;
    }

}
