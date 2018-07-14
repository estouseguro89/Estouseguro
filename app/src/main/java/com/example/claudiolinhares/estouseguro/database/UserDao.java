package com.example.claudiolinhares.estouseguro.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE cpf LIKE :cpf AND password LIKE :password LIMIT 1")
    User findLogin(String cpf, String password);

    @Query("SELECT * FROM user WHERE cpf LIKE :cpf LIMIT 1")
    User findAlreadyExist(String cpf);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
