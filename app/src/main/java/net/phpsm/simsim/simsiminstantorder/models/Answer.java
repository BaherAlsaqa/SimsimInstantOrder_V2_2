package net.phpsm.simsim.simsiminstantorder.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baher on 20/12/2017.
 */

public class Answer implements Parcelable {
    private String answer;

    public Answer(Parcel in) {
        answer = in.readString();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answer(String answer1) {
        this.answer = answer1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };


}
