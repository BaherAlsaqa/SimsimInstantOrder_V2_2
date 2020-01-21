package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyFriendRecommendedList;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 17/01/2018.
 */

public class MyFriendRecommendedItems {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public MyFriendRecommendedList myFriendRecommendedList;

    public MyFriendRecommendedItems(String status, MyFriendRecommendedList myFriendRecommendedList) {
        this.status = status;
        this.myFriendRecommendedList = myFriendRecommendedList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyFriendRecommendedList getMyFriendRecommendedList() {
        return myFriendRecommendedList;
    }

    public void setMyFriendRecommendedList(MyFriendRecommendedList myFriendRecommendedList) {
        this.myFriendRecommendedList = myFriendRecommendedList;
    }
}
