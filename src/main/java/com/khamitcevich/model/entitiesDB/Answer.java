package com.khamitcevich.model.entitiesDB;

public class Answer {
    private final int id;
    private int idQuestion;
    private String version;
    private String isRight;

    public Answer (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", idQuestion=" + idQuestion +
                ", version='" + version + '\'' +
                ", isRight='" + isRight + '\'' +
                '}';
    }
}
