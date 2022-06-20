package com.teslyuk.android.androidtutorial.lab3;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void insert(UserData userData);

    @Delete
    void delete(UserData userData);

    @Delete
    void reset(List<UserData> userData);

    @Query("UPDATE user SET login = :sLogin,password = :sPassword,token = :sToken,location = :sLocation WHERE ID = :sID")
    void update(int sID,String sLogin,String sPassword,String sToken,String sLocation);

    @Query("SELECT * FROM user")
    List<UserData> getAll();
}
