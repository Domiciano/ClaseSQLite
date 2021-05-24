package edu.co.icesi.claseauth.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    private AppDatabase(){}

    public static AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "dblastclass").allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract UserDao userDao();

}
