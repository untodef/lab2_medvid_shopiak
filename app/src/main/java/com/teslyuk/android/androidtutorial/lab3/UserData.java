package com.teslyuk.android.androidtutorial.lab3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class UserData  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private  int ID;

    @ColumnInfo(name = "login")
    private String login;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "token")
    private String token;
    @ColumnInfo(name = "location")
    private String location;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
