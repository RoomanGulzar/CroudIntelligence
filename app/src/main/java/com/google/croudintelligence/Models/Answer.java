package com.google.croudintelligence.Models;

public class Answer {
    private int answer_id;
    private int question_id;
    private int user_id;
    private String answer_text;
    private String answer_image;
    private String answer_date;
    public User user;

    public String getAnswer_date() {
        return answer_date == null ? " T " : answer_date;
    }

    public void setAnswer_date(String answer_date) {
        this.answer_date = answer_date.split("T")[0];
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

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

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public String getAnswer_image() {
        return answer_image;
    }

    public void setAnswer_image(String answer_image) {
        this.answer_image = answer_image;
    }
}
