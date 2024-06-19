package be.ucll.jmelektromanex.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import be.ucll.jmelektromanex.entities.WorkOrder;

@Dao
public interface WorkOrderDao {

    //CREATE
    @Insert
    void insertWorkOrders(WorkOrder...workOrders);

    // Inserting 1 WorkOrder
    @Insert
    ListenableFuture<Void> insertWorkOrder(WorkOrder workOrder);

    //READ all
    @Query("SELECT * FROM WorkOrder")
    LiveData<List<WorkOrder>> getAllWorkOrders();

    //UPDATE
    @Update
    void updateWorkOrder(WorkOrder workOrder);

    //DELETE
    @Delete
    void deleteWorkOrder(WorkOrder workOrder);

}

