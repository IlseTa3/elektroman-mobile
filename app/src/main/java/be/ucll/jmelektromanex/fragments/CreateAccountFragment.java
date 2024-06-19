package be.ucll.jmelektromanex.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import be.ucll.jmelektromanex.R;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.repository.UserRepository;
import be.ucll.jmelektromanex.viewmodels.UserViewModel;
import be.ucll.jmelektromanex.viewmodels.UserViewModelFactory;

public class CreateAccountFragment extends Fragment {
    private UserViewModel userViewModel;
    private ViewModelProvider.Factory factory;

    private Button createAccountBtn, backBtn;
    private CheckBox termsCondCheckBx;

    private UserRepository userRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(requireContext());
        factory = new UserViewModelFactory(userRepository);
        userViewModel = new ViewModelProvider(this, factory)
                .get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        backBtn = view.findViewById(R.id.btnBack);
        createAccountBtn = view.findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserAccount();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginFragment();
            }
        });

        return view;
    }

    private void navigateToLoginFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigateUp();
    }

    private void createUserAccount(){
        //Controle Terms and Conditions
        termsCondCheckBx = getView().findViewById(R.id.termsCondCheckBx);
        if(!termsCondCheckBx.isChecked()){
            Toast.makeText(requireContext(), "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        //gegevens ophalen vanuit create acount fragment - layout
        String firstName = ((EditText) getView().findViewById(R.id.createFirstName)).getText().toString();
        String lastName = ((EditText) getView().findViewById(R.id.createLastName)).getText().toString();
        EditText dateOfBirthEditText = getView().findViewById(R.id.createDateOfBirth);
        LocalDate dateOfBirth = null;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateOfBirth = LocalDate.parse(dateOfBirthEditText.getText().toString(),formatter);
        }catch (DateTimeParseException e){
            Toast.makeText(requireContext(),"Please enter a valid date of Birth (dd/mm/yyyy",
                    Toast.LENGTH_SHORT).show();
        }
        String street = ((EditText) getView().findViewById(R.id.createStreet)).getText().toString();
        String housNr = ((EditText) getView().findViewById(R.id.createHouseNr)).getText().toString();
        String boxNr = ((EditText) getView().findViewById(R.id.createBoxNr)).getText().toString();
        String postalcode = ((EditText) getView().findViewById(R.id.createPostalcode)).getText().toString();
        String municipality = ((EditText) getView().findViewById(R.id.createMunicipality)).getText().toString();
        String username = ((EditText) getView().findViewById(R.id.createUsername)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.createPassword)).getText().toString();


        //Controle velden ingevuld?
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)|| dateOfBirth == null
                || TextUtils.isEmpty(street)|| TextUtils.isEmpty(housNr)|| TextUtils.isEmpty(boxNr)
                || TextUtils.isEmpty(postalcode)|| TextUtils.isEmpty(municipality)|| TextUtils.isEmpty(username)
                || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(),"Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        //Aanmaken new user
        User newUser = new User(firstName,lastName,dateOfBirth,street,
                housNr,boxNr,postalcode,municipality,username,password);
        userViewModel.createAccountUser(newUser);
        navigateToLoginFragment();
    }

}