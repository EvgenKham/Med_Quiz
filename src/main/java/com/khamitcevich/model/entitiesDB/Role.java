package com.khamitcevich.model.entitiesDB;

public class Role {
    private final int id;
    private String type;

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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type +
                '}';
    }
}
