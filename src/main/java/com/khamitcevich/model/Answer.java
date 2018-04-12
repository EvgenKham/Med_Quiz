package com.khamitcevich.model;

public class Answer {
    private final int id;
    private final int idQuestion = 0;
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
