package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class NotificationObject {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public NotificationItemsObject notificationItemsObject;

    public NotificationObject(String status, NotificationItemsObject notificationItemsObject1) {
        this.status = status;
        this.notificationItemsObject = notificationItemsObject1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationItemsObject getNotificationItemsObject() {
        return notificationItemsObject;
    }

    public void setNotificationItemsObject(NotificationItemsObject notificationItemsObject1) {
        this.notificationItemsObject = notificationItemsObject1;
    }
}
