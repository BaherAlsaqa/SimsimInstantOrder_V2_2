package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MySavedList;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 17/01/2018.
 */

public class MySavedItems {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public MySavedList myFriendRecommendedList;

    public MySavedItems(String status, MySavedList myFriendRecommendedList) {
        this.status = status;
        this.myFriendRecommendedList = myFriendRecommendedList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MySavedList getMyFriendRecommendedList() {
        return myFriendRecommendedList;
    }

    public void setMyFriendRecommendedList(MySavedList myFriendRecommendedList) {
        this.myFriendRecommendedList = myFriendRecommendedList;
    }
}
