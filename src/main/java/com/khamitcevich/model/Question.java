package com.khamitcevich.model;

public class Question {
    private final int id;
    private final int idCategory = 0;
    private String body;

    public Question (int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", idCategory=" + idCategory +
                ", body='" + body + '\'' +
                '}';
    }
}
