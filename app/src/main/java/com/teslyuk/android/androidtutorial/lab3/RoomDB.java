package com.teslyuk.android.androidtutorial.lab3;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "user_database";

    public synchronized  static RoomDB getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext()
                    ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();


        }return database;
    }

    public abstract UserDao userDao();
}
