package com.example.ofirmonis.resttodo.objects;


public class Review {
    private String user_id;
    private String rest_id;
    private String review;
    private String date;

    public Review() {
    }

    public Review(String user_id, String rest_id, String review, String date) {
        this.user_id = user_id;
        this.rest_id = rest_id;
        this.review = review;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getRest_id() {
        return rest_id;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }
}
