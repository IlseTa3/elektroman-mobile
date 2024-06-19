package be.ucll.jmelektromanex.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import be.ucll.jmelektromanex.R;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.repository.UserRepository;
import be.ucll.jmelektromanex.viewmodels.UserViewModel;
import be.ucll.jmelektromanex.viewmodels.UserViewModelFactory;

public class LoginFragment extends Fragment {
    private EditText usernameEdit, passwordEdit;
    private Button loginButton,createAccountBtn;
    private TextView messageTextView;
    private UserRepository repo;

    private UserViewModel userViewModel;
    private UserViewModelFactory userViewModelFactory;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        repo = new UserRepository(requireContext());
        userViewModelFactory = new UserViewModelFactory(repo);
        userViewModel = new ViewModelProvider(this, userViewModelFactory).get(UserViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Find views By ID's
        usernameEdit = view.findViewById(R.id.username);
        passwordEdit = view.findViewById(R.id.password);

        loginButton = view.findViewById(R.id.loginBtn);
        messageTextView = view.findViewById(R.id.correctUserDataTxtView);
        createAccountBtn = view.findViewById(R.id.CreateAccountBtn);

        //login knop
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                User user = userViewModel.getUser(username);
                int colorCorrect = ContextCompat.getColor(requireContext(),R.color.green);
                int colorIncorrect = ContextCompat.getColor(requireContext(),R.color.red);
                if(user != null){
                    if(user.getPassword().equals(password)){
                        messageTextView.setText("Login successful, Redirecting...");
                        messageTextView.setTextColor(colorCorrect);
                        messageTextView.setVisibility(View.VISIBLE);
                        SharedPreferences sharedPreferences = requireContext()
                                .getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        new Handler().postDelayed(()-> {
                            navigateToWorkOrderListFragment();
                        },3000);
                    }else{
                        messageTextView.setText("Incorrect username or password.");
                        messageTextView.setTextColor(colorIncorrect);
                        messageTextView.setVisibility(View.VISIBLE);
                    }
                }else{
                    messageTextView.setText("User was not found in the database");
                    messageTextView.setTextColor(colorIncorrect);
                    messageTextView.setVisibility(View.VISIBLE);
                }

            }
        });

        //Knop Create Account te laten werken
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateAccountFragment();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void navigateToCreateAccountFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_loginFragment_to_createAccountFragment);
    }



    private void navigateToWorkOrderListFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_loginFragment_to_workOrderListFragment);
    }

}