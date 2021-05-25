package edu.co.icesi.claseauth.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<UserDB> getAll();

}

