package be.ucll.jmelektromanex.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    private LiveData<List<User>> allUsers;

    public UserViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
        allUsers = userRepository.getAllUsers();
    }


    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }


    public User getUser(String username){
        Log.d("UserRepository", "getUserByName: Getting user by username: " + username);
        try{
            return userRepository.getUserByName(username);
        }catch (Exception e){
            Log.e("UserViewModel", "getUser: Error getting user: ", e);
            throw new RuntimeException(e);
        }
    }

    public LiveData<String> getFullNameByUsername(String username){
        return userRepository.getFullNameByUsername(username);
    }

    public void createAccountUser(User user){
        userRepository.createUser(user);
    }

    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }


}

