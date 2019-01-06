package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GithubUserConverters {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<User> stringToContact(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<User>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String contactToString(List<User> contacts) {
        return gson.toJson(contacts);
    }

}
