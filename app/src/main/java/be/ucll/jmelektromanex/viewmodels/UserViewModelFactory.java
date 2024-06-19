package be.ucll.jmelektromanex.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import be.ucll.jmelektromanex.repository.UserRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private UserRepository repository;

    public UserViewModelFactory(UserRepository userRepository){
        this.repository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
