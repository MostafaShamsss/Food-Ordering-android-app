package com.example.wagbaproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
interface Daoclass {

    @Insert
    void insertAllData(UserModel model);

    //Select All Data
    @Query("select * from  user")
    List<UserModel> getAllData();

    //DELETE DATA
    @Query("delete from user where `key`= :id")
    void deleteData(int id);

    //Update Data

    @Query("update user SET name= :name, email =:email ,address =:address, phoneno =:phoneno where `key`= :key")
    void updateData(String name, String email, String phoneno, String address, int key);


    @Query("SELECT * FROM user where email like :email LIMIT 1")
    LiveData<UserModel> findByEmail(String email);


}
