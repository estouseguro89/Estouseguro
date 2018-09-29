package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GithubTypeConverters {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Contact> stringToContact(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Contact>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String contactToString(List<Contact> contacts) {
        return gson.toJson(contacts);
    }

}
