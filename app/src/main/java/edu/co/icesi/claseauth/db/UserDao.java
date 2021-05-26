package edu.co.icesi.claseauth.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {



    @Query("INSERT OR REPLACE INTO users(id, name,email, city) VALUES (:id, :name, :email, :city)")
    void insertOrReplace(String id, String name, String email, String city);

    @Query("SELECT * FROM users WHERE id=:id")
    UserDB getUserById(String id);

    //SQL

}

