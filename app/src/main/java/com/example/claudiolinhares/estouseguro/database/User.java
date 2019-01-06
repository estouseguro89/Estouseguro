package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

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
    @ColumnInfo(name = "configsms")
    private boolean configsms;
    @ColumnInfo(name = "configlocation")
    private boolean configlocation;
    @ColumnInfo(name = "configemail")
    private boolean configemail;
    //@ColumnInfo(name = "contacts")
    //@Relation(parentColumn = "id", entityColumn = "id", entity = Contact.class, projection = {"name"})
    @TypeConverters(GithubTypeConverters.class)
    private List<Contact> sendcontact;
    @TypeConverters(GithubTypeConverters.class)
    private List<Contact> receivedcontact;
    @TypeConverters(GithubUserConverters.class)
    private List<User> contacts;

    public User()
    {

    }

    public User(String cpf, String password,String name,String lastname,String email, String telefone, List<User> contacts, List<Contact> send, List<Contact> received)
    {
        this.cpf = cpf;
        this.password = password;
        this.setName(name);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setContacts(contacts);
        this.setConfigsms(false);
        this.setConfiglocation(false);
        this.setConfigemail(false);
        this.setSendcontact(send);
        this.setReceivedcontact(received);
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

    public void setAllContacts(List<User> contacts) {
        this.setContacts(contacts);
    }

    public boolean isConfigsms() {
        return configsms;
    }

    public void setConfigsms(boolean configsms) {
        this.configsms = configsms;
    }

    public boolean isConfiglocation() {
        return configlocation;
    }

    public void setConfiglocation(boolean configlocation) {
        this.configlocation = configlocation;
    }

    public boolean isConfigemail() { return configemail; }

    public void setConfigemail(boolean configemail) { this.configemail = configemail; }

    public List<Contact> getSendcontact() {
        return sendcontact;
    }

    public void setSendcontact(List<Contact> sendcontact) {
        this.sendcontact = sendcontact;
    }

    public List<Contact> getReceivedcontact() {
        return receivedcontact;
    }

    public void setReceivedcontact(List<Contact> receivedcontact) {
        this.receivedcontact = receivedcontact;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public void setOneReceivedcontact(Contact receivedcontact) {
        this.receivedcontact.add(receivedcontact);
    }

    public void setOneSendcontact(Contact sendcontact) {
        this.sendcontact.add(sendcontact);
    }

    public void setOneContact(User contact) {
        this.contacts.add(contact);
    }
}