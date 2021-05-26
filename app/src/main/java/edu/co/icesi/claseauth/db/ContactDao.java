package edu.co.icesi.claseauth.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {


    @Query("SELECT * FROM contacts WHERE userID=:userID")
    List<ContactDB> getAll(String userID);

    @Query("INSERT INTO contacts(id, name, phone, userID) VALUES (:id,:name,:phone,:userID)")
    void insertContact(String id, String name, String phone, String userID);

    @Query("DELETE FROM contacts WHERE phone=:phone")
    void deleteByPhone(String phone);

    //SQL

}

