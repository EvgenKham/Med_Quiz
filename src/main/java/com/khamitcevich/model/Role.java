package com.khamitcevich.model;

public class Role {
    private final int id;
    private String type;
    private final int idUser = 0;

    public Role (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
