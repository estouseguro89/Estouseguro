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
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "lastname")
    private String lastname;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "telefone")
    private String telefone;
    @ColumnInfo(name = "password")
    private String password;

    public User(String cpf, String password,String name,String lastname,String email, String telefone)
    {
        this.cpf = cpf;
        this.password = password;
        this.setName(name);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setTelefone(telefone);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}