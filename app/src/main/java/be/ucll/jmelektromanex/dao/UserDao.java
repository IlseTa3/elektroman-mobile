package be.ucll.jmelektromanex.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.UserWithWorkOrders;

@Dao
public interface UserDao {

    //Create or Insert a User
    @Insert
    void insertUser(User user);

    @Query("select * from User")
    LiveData<List<User>> getAllUsers();

    //Get user by Username:
    @Query("SELECT * FROM User WHERE username = :username")
    ListenableFuture<User> getUserByUsername(String username);

    @Query("SELECT firstName || ' ' || lastName AS fullName FROM User WHERE username = :username")
    LiveData<String> getFullNameByUserName(String username);


    @Query("SELECT * from User")
    @Transaction
    LiveData<List<UserWithWorkOrders>> getUsersWithWorkOrderLists();

    @Transaction
    @Query("select * from User where user.username = :username")
    ListenableFuture<UserWithWorkOrders> getWorkOrdersFromUser(String username);


    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}