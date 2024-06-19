package be.ucll.jmelektromanex.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import be.ucll.jmelektromanex.dao.UserDao;
import be.ucll.jmelektromanex.database.JmElektroManEx_database;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.UserWithWorkOrders;

public class UserRepository {
    private JmElektroManEx_database database;
    private UserDao userDao;

    private LiveData<List<User>> allUsers;
    private LiveData<List<UserWithWorkOrders>> userWithWorkOrders;

    public UserRepository(Context context){
        database = JmElektroManEx_database.getDatabase(context);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        userWithWorkOrders = userDao.getUsersWithWorkOrderLists();
    }


    //Aanmaken van een User (CREATE)
    public void createUser(User user){
        database.executeOnBackgroundThread(() -> {
            userDao.insertUser(user);
            Log.d("UserRepository", "User created: " + user.toString());
        });

    }

    //Opvragen van ALLE Users (READ ALL)
    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    //Opvragen van een User (READ) op naam
    public User getUserByName(String username) throws ExecutionException, InterruptedException {
        Log.d("UserRepository", "getUserByName: Getting user by username: " + username);
        return userDao.getUserByUsername(username).get();
    }

    public LiveData<String> getFullNameByUsername(String username){
        return userDao.getFullNameByUserName(username);
    }


    public ListenableFuture<UserWithWorkOrders> getWorkOrdersFromUser(String username){
        return userDao.getWorkOrdersFromUser(username);
    }

    //Updaten van een User (UPDATE)
    public void updateUser(User user){
        database.executeOnBackgroundThread(() -> {
            userDao.updateUser(user);
        });
    }

    //Verwijderen van een User (DELETE)
    public void deleteUser(User user){
        database.executeOnBackgroundThread(() -> {
            userDao.deleteUser(user);
        });
    }

}
