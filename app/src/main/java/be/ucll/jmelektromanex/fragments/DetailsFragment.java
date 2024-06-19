package be.ucll.jmelektromanex.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import be.ucll.jmelektromanex.R;
import be.ucll.jmelektromanex.entities.WorkOrder;
import be.ucll.jmelektromanex.repository.WorkOrderRepository;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModel;
import be.ucll.jmelektromanex.viewmodels.WorkOrderViewModelFactory;

public class DetailsFragment extends Fragment {

    private WorkOrderViewModelFactory factory;
    private WorkOrderViewModel workOrderViewModel;
    private WorkOrderRepository workOrderRepository;

    private EditText editTxtProblemDesc, editTxtRepairInfo;
    private TextView txtVwMessageDetails;

    private WorkOrder workOrder;

    private boolean processed;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workOrderRepository = new WorkOrderRepository(requireContext());
        factory = new WorkOrderViewModelFactory(workOrderRepository);
        workOrderViewModel = new ViewModelProvider(this, factory).get(WorkOrderViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        editTxtProblemDesc = view.findViewById(R.id.editTxtProblemDesc);
        editTxtRepairInfo = view.findViewById(R.id.EditTxtRepairInfo);
        txtVwMessageDetails = view.findViewById(R.id.txtVwMessageDetails);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_details);
        MenuItem saveItem = toolbar.getMenu().findItem(R.id.action_save);
        MenuItem cancelItem = toolbar.getMenu().findItem(R.id.action_cancel);
        MenuItem reopenItem = toolbar.getMenu().findItem(R.id.action_reopen);
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d("Toolbar", "setOnMenuItem geactiveerd");
            int itemId = item.getItemId();
            if (itemId == R.id.action_save){
                saveDetailWorkOrder();
                return true;
            } else if (itemId == R.id.action_cancel) {
                navigateToWorkOrderListFragment();
            } else if (itemId == R.id.action_reopen) {
                reopenWorkOrder();
            }
            return true;
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            workOrder = (WorkOrder) bundle.getSerializable("workOrder");
            processed = bundle.getBoolean("processed", false);
            if(workOrder != null){
                editTxtProblemDesc.setText(workOrder.getDetailedProblemDescription());
                editTxtRepairInfo.setText(workOrder.getRepairInformation());
                if(processed){
                    editTxtProblemDesc.setEnabled(false);
                    editTxtRepairInfo.setEnabled(false);
                    saveItem.setVisible(false);
                    cancelItem.setVisible(true);
                    reopenItem.setVisible(true);
                }else{
                    saveItem.setVisible(true);
                    cancelItem.setVisible(true);
                    reopenItem.setVisible(false);
                }
            }
        }



        return view;
    }

    private void navigateToWorkOrderListFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigateUp();
    }

    private void saveDetailWorkOrder(){
        String problemDesc = editTxtProblemDesc.getText().toString();
        String repairInfo = editTxtRepairInfo.getText().toString();
        int color = ContextCompat.getColor(requireContext(), R.color.red);

        if(repairInfo.trim().isEmpty()){
            txtVwMessageDetails.setText("Not saved. No repair information was entered!");
            txtVwMessageDetails.setTextColor(color);
        } else{
            workOrder.setDetailedProblemDescription(problemDesc);
            workOrder.setRepairInformation(repairInfo);
            workOrderViewModel.updateWorkOrder(workOrder);
            navigateToWorkOrderListFragment();
        }

    }

    private void reopenWorkOrder(){
        workOrder.setProcessed(false);
        workOrderViewModel.updateWorkOrder(workOrder);
        navigateToWorkOrderListFragment();
    }



}