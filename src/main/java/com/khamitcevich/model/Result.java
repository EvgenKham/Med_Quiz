package com.khamitcevich.model;

import javax.xml.crypto.Data;

public class Result {
    private final int id;
    private String typeTest;
    private float percent;
    private Data data;
    private final int idUser = 0;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", typeTest='" + typeTest + '\'' +
                ", percent=" + percent +
                ", data=" + data +
                ", idUser=" + idUser +
                '}';
    }
}
