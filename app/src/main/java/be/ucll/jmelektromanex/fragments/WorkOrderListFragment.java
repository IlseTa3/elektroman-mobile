package be.ucll.jmelektromanex.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.ucll.jmelektromanex.R;
import be.ucll.jmelektromanex.WorkOrderAdapter;
import be.ucll.jmelektromanex.entities.WorkOrder;
import be.ucll.jmelektromanex.repository.UserRepository;
import be.ucll.jmelektromanex.repository.WorkOrderRepository;
import be.ucll.jmelektromanex.viewmodels.UserViewModel;
import be.ucll.jmelektromanex.viewmodels.UserViewModelFactory;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModel;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModelFactory;

public class WorkOrderListFragment extends Fragment {

    private TextView welcomeMsgTextView;
    private WorkOrderViewModel workOrderViewModel;

    private WorkOrderViewModelFactory workOrderViewModelFactory;

    private UserViewModelFactory userViewModelFactory;

    private UserViewModel userViewModel;

    private UserRepository userRepository;
    private WorkOrderRepository workOrderRepository;
    private SharedPreferences sharedPreferences;

    private WorkOrderAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences("my_preference", Context.MODE_PRIVATE);
        userRepository = new UserRepository(requireContext());
        workOrderRepository = new WorkOrderRepository(requireContext());
        userViewModelFactory = new UserViewModelFactory(userRepository);
        workOrderViewModelFactory = new WorkOrderViewModelFactory(workOrderRepository);
        userViewModel = new ViewModelProvider(this,userViewModelFactory).get(UserViewModel.class);
        workOrderViewModel = new ViewModelProvider(this, workOrderViewModelFactory).get(WorkOrderViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_order_list, container, false);
        welcomeMsgTextView = view.findViewById(R.id.welcomeMessageTextView);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d("Toolbar", "setOnMenuItem geactiveerd");
            int itemId = item.getItemId();
            if (itemId == R.id.action_new_work_order){
                navigateToWorkOrderFragment();
                return true;
            }
            return true;
        });


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WorkOrderAdapter();

        recyclerView.setAdapter(adapter);

        workOrderViewModel.getAllWorkOrders().observe(getViewLifecycleOwner(), new Observer<List<WorkOrder>>() {
            @Override
            public void onChanged(List<WorkOrder> workOrders) {
                adapter.submit(workOrders);
            }
        });

        adapter.setOnItemClickListener(new WorkOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WorkOrder workOrder) {
                navigateToDetailsFragment(workOrder);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        // Gebruikersnaam doorgeven aan ViewModel om de volledige naam op te halen
        userViewModel.getFullNameByUsername(username).observe(getViewLifecycleOwner(), fullName -> {
            if (fullName != null) {
                Log.d("LoginFragment", "Received full name: " + fullName); // Controleer of de juiste volledige naam wordt ontvangen
                String welcomeMessage = "Welkom, " + fullName + "!";
                Log.d("LoginFragment", "Welcome message: " + welcomeMessage);
                welcomeMsgTextView.setText(welcomeMessage);
            }
        });

    }

    private void navigateToWorkOrderFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_workOrderListFragment_to_workOrderFragment);
    }

    private void navigateToDetailsFragment(WorkOrder workOrder){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        Bundle bundle = new Bundle();
        bundle.putSerializable("workOrder", workOrder);
        bundle.putBoolean("processed", workOrder.isProcessed());
        navController.navigate(R.id.action_workOrderListFragment_to_detailsFragment,bundle);

    }



}