package be.ucll.jmelektromanex.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.UserWithWorkOrders;
import be.ucll.jmelektromanex.entities.WorkOrder;
import be.ucll.jmelektromanex.repository.WorkOrderRepository;

public class WorkOrderViewModel extends ViewModel {
    private WorkOrderRepository workOrderRepository;
    private LiveData<List<WorkOrder>> allWorkOrders;

    private LiveData<WorkOrder> workOrder;

    private void init(){
        allWorkOrders = workOrderRepository.getAllWorkOrders();
    }
    public WorkOrderViewModel(WorkOrderRepository workOrderRepository){
        this.workOrderRepository = workOrderRepository;
        init();
    }



    public LiveData<List<WorkOrder>> getAllWorkOrders(){
        return allWorkOrders;
    }


    public ListenableFuture<UserWithWorkOrders> getWorkOrdersFromUser(String username){
        return workOrderRepository.getWorkOrdersFromUser(username);
    }
    public void createWorkOrder(WorkOrder workOrder){
        workOrderRepository.createWorkOrder(workOrder);
    }


    public void updateWorkOrder(WorkOrder workOrder){
        workOrderRepository.updateWorkOrder(workOrder);
    }

    public void deleteWorkOrder(WorkOrder workOrder){
        workOrderRepository.deleteWorkOrder(workOrder);
    }


}
