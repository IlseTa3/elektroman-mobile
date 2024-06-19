package be.ucll.jmelektromanex.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import be.ucll.jmelektromanex.R;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.WorkOrder;
import be.ucll.jmelektromanex.repository.WorkOrderRepository;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModel;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModelFactory;

public class WorkOrderFragment extends Fragment {


    private EditText cityEdit, deviceEdit,problemCodeEdit,nameCustomerEdit;

    private TextView statusTxtVw;

    private WorkOrderViewModel workOrderViewModel;
    private WorkOrderViewModelFactory factory;
    private WorkOrderRepository repo;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new WorkOrderRepository(requireContext());
        factory = new WorkOrderViewModelFactory(repo);
        workOrderViewModel = new ViewModelProvider(this, factory).get(WorkOrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_work_order, container, false);
        cityEdit = view.findViewById(R.id.editTextCity);
        deviceEdit = view.findViewById(R.id.editTextDevice);
        problemCodeEdit = view.findViewById(R.id.editTextProblemCode);
        nameCustomerEdit = view.findViewById(R.id.editTextNameCust);
        statusTxtVw = view.findViewById(R.id.statusTxtVw);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_workorder);
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d("Toolbar", "setOnMenuItem geactiveerd");
            int itemId = item.getItemId();
            if (itemId == R.id.action_save){
                addNewOrder();
                return true;
            } else if (itemId == R.id.action_cancel) {
                navigateToWorkOrderListFragment();
            }
            return true;
        });


        return view;
    }

    private void navigateToWorkOrderListFragment(){
        if(isAdded()){
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                    .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigateUp();
        }

    }



    private void addNewOrder(){
        String city = cityEdit.getText().toString();
        String device = deviceEdit.getText().toString();
        String problemCode = problemCodeEdit.getText().toString();
        String nameCustomer = nameCustomerEdit.getText().toString();

        if(TextUtils.isEmpty(city)|| TextUtils.isEmpty(device)|| TextUtils.isEmpty(problemCode)
                || TextUtils.isEmpty(nameCustomer)){
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        WorkOrder newWorkOrder = new WorkOrder(city,device,problemCode,nameCustomer,false);
        int colorCorrect = ContextCompat.getColor(requireContext(),R.color.green);
        int colorIncorrect = ContextCompat.getColor(requireContext(),R.color.red);

        String currentUser = getCurrentUser();
        if(currentUser != null){
            workOrderViewModel.createWorkOrder(newWorkOrder);
            statusTxtVw.setText("Work order added successfully!");
            statusTxtVw.setTextColor(colorCorrect);
            statusTxtVw.setVisibility(View.VISIBLE);
            new Handler().postDelayed(this::navigateToWorkOrderListFragment,3000);
        }else{
            statusTxtVw.setText("Failed to add workorder");
            statusTxtVw.setTextColor(colorIncorrect);
            statusTxtVw.setVisibility(View.VISIBLE);
            new Handler().postDelayed(this::navigateToWorkOrderListFragment,3000);
        }

    }

    private String getCurrentUser(){
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        return username;
    }

}