package com.example.trandar.http1;

/**
 * Created by nutto on 27/7/2558.
 */
public class Model {
    String username;
    String password;

    @Override
    public String toString() {
        return "Model{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Model(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
