package com.google.croudintelligence.ViewModel;

import androidx.annotation.NonNull;

public class Question {
    private int question_id;
    private int user_id;
    private String user_name;
    private String email;
    private String contact;
    private String password;
    private String role;
    private String pic;
    private int t_id;
    private String question_text;
    private String date_created;


    // getters and setters


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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        return date_created == null ? " " : date_created.split("T")[0];
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", pic='" + pic + '\'' +
                ", t_id=" + t_id +
                ", question_text='" + question_text + '\'' +
                ", date_created='" + date_created + '\'' +
                '}';
    }

   /* public static void main(String[] args) {
        String json = "{\"question_id\":8,\"user_id\":2205,\"user_name\":\"Ali\",\"email\":\"ali@123\",\"contact\":\"12345\",\"password\":\"as\",\"role\":\"User\",\"pic\":\"C:\\\\Pictures\\\\Images\\\\Ali.jpg\",\"t_id\":2,\"question_text\":\"What are some promising new treatments for cancer, and how do they differ from traditional chemotherapy and radiation therapy?\",\"date_created\":\"2023-05-04T00:00:00\"}";

        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);

        System.out.println(question);
    }*/
}
