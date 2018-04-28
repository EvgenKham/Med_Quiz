package com.khamitcevich.model.entitiesDB;

public class Question {

    private final int id;
    private int idCategory;
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

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "QuestionDao{" +
                "id=" + id +
                ", idCategory=" + idCategory +
                ", body='" + body + '\'' +
                '}';
    }
}
