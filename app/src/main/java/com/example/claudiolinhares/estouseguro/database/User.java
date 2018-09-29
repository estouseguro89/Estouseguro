package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

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
    //@ColumnInfo(name = "contacts")
    //@Relation(parentColumn = "id", entityColumn = "id", entity = Contact.class, projection = {"name"})
    @TypeConverters(GithubTypeConverters.class)
    private List<Contact> contacts;

    public User(String cpf, String password,String name,String lastname,String email, String telefone, List<Contact> contacts)
    {
        this.cpf = cpf;
        this.password = password;
        this.setName(name);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.contacts = contacts;
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Contact contact) {
        this.contacts.add(contact);
    }

    public void setAllContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}