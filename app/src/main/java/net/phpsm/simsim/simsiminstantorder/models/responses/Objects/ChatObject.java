package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class ChatObject {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public ChatItemsObject chatItemsObject;

    public ChatObject(String status, ChatItemsObject chatItemsObject1) {
        this.status = status;
        this.chatItemsObject = chatItemsObject1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChatItemsObject getChatItemsObject() {
        return chatItemsObject;
    }

    public void setChatItemsObject(ChatItemsObject homeItemsObject1) {
        this.chatItemsObject = homeItemsObject1;
    }
}
