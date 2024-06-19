package be.ucll.jmelektromanex.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithWorkOrders {
    @Embedded
    public User user;
    @Relation(parentColumn = "userId",
            entityColumn = "userWorkOrderId")
    public List<WorkOrder> workOrderList;

    public UserWithWorkOrders() {
    }

    public UserWithWorkOrders(User user, WorkOrder workOrder) {
    }

}
