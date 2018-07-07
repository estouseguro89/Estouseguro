package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User{
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "cpf")
    private String cpf;
    @ColumnInfo(name = "password")
    private String password;

    public User(String cpf, String password)
    {
        this.cpf = cpf;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}