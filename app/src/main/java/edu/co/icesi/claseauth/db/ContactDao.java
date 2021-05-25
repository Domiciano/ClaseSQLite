package edu.co.icesi.claseauth.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts WHERE userID = :userID")
    List<UserDB> getAll(String userID);

}

