package com.khamitcevich.model;

public class User {
    private final int id;
    private String login;
    private String password;
    private String email;
    private int timesheetNumber;

    public User (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTimesheetNumber() {
        return timesheetNumber;
    }

    public User newUser (String login, String password, String email, int timesheetNumber) {
        this.setLogin(login);
        this.setPassword(password);
        this.setEmail(email);
        this.setTimesheetNumber(timesheetNumber);
        return this;
    }

    public void setTimesheetNumber(int timesheetNumber) {
        this.timesheetNumber = timesheetNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", timesheetNumber=" + timesheetNumber +
                '}';
    }
}
