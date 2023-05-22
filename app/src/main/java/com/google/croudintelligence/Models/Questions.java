package com.google.croudintelligence.Models;

public class Questions {
    private int question_id;
    private int user_id;
    private int t_id;
    private String question_text;
    private String date_created;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getDate_created() {
        return date_created == null || date_created.equals("") ? "" : date_created.split("T")[0];
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
