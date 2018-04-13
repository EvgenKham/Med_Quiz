package com.khamitcevich.model.entitiesDB;

import java.util.Date;

public class Result {
    private final int id;
    private String typeTest;
    private float percent;
    private Date date;
    private int idUser;

    public Result (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTypeTest() {
        return typeTest;
    }

    public void setTypeTest(String typeTest) {
        this.typeTest = typeTest;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", typeTest='" + typeTest + '\'' +
                ", percent=" + percent +
                ", data=" + date +
                ", idUser=" + idUser +
                '}';
    }
}
