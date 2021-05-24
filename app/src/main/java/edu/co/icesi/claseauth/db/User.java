package edu.co.icesi.claseauth.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "email")
    public String city;
}
