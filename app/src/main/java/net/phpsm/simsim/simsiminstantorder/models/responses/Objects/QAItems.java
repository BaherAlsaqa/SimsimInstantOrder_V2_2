package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

public class QAItems {

    @SerializedName("id")
    int id;
    @SerializedName("question_text")
    String question_text;
    @SerializedName("answer_text")
    String answer_text;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("category_id")
    int category_id;

    public QAItems() {
    }

    public QAItems(int id, String question_text, String answer_text, String created_at, String updated_at, int category_id) {
        this.id = id;
        this.question_text = question_text;
        this.answer_text = answer_text;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
