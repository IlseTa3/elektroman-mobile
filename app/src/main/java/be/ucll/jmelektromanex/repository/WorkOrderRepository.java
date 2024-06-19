package be.ucll.jmelektromanex.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

import be.ucll.jmelektromanex.dao.UserDao;
import be.ucll.jmelektromanex.dao.WorkOrderDao;
import be.ucll.jmelektromanex.database.JmElektroManEx_database;
import be.ucll.jmelektromanex.entities.User;
import be.ucll.jmelektromanex.entities.UserWithWorkOrders;
import be.ucll.jmelektromanex.entities.WorkOrder;

public class WorkOrderRepository {
    private JmElektroManEx_database database;
    private WorkOrderDao workOrderDao;

    private UserDao userDao;

    private LiveData<List<WorkOrder>> allWorkOrders;



    public WorkOrderRepository(Context context){
        database = JmElektroManEx_database.getDatabase(context);
        workOrderDao = database.workOrderDao();
        userDao = database.userDao();
        allWorkOrders = workOrderDao.getAllWorkOrders();

    }

    public ListenableFuture<UserWithWorkOrders> getWorkOrdersFromUser(String username){
        return userDao.getWorkOrdersFromUser(username);
    }


    //Aanmaken van een Workorder (CREATE)
    public void createWorkOrder(WorkOrder workOrder){
        database.executeOnBackgroundThread(()->{
            workOrderDao.insertWorkOrder(workOrder);
        });
    }

    //Opvragen ALLE workorders (READ ALL)
    public LiveData<List<WorkOrder>> getAllWorkOrders(){
        return allWorkOrders;
    }


    //Updaten van WorkOrder (UPDATE)
    public void updateWorkOrder(WorkOrder workOrder){
        database.executeOnBackgroundThread(() -> {
            workOrderDao.updateWorkOrder(workOrder);
        });
    }

    //Verwijderen van WorkOrder (DELETE)
    public void deleteWorkOrder(WorkOrder workOrder){
        database.executeOnBackgroundThread(() -> {
            workOrderDao.deleteWorkOrder(workOrder);
        });
    }

}
