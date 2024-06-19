package be.ucll.jmelektromanex.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WorkOrder implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long workOrderId;

    private Long userWorkOrderId;//foreign key

    private String city;
    private String device;
    private String problemCode;
    private String customerName;
    private boolean processed;
    private String detailedProblemDescription;
    private String repairInformation;

    private static final long serialVersionUID = 1L;
    public WorkOrder() {
    }

    public WorkOrder(String city, String device, String problemCode, String nameCustomer, boolean processed) {
        this.city = city;
        this.device = device;
        this.problemCode = problemCode;
        this.customerName = nameCustomer;
        this.processed = processed;
    }

    public WorkOrder(String city, String device, String problemCode, String nameCustomer, boolean processed, String detailedProblemDescription) {
        this.city = city;
        this.device = device;
        this.problemCode = problemCode;
        this.customerName = nameCustomer;
        this.processed = processed;
        this.detailedProblemDescription = detailedProblemDescription;

    }

    public WorkOrder(String city, String device, String problemCode, String nameCustomer, boolean processed, String detailedProblemDescription,
                     String repairInformation) {
        this.city = city;
        this.device = device;
        this.problemCode = problemCode;
        this.customerName = nameCustomer;
        this.processed = processed;
        this.detailedProblemDescription = detailedProblemDescription;
        this.repairInformation = repairInformation;
    }


    public WorkOrder( boolean processed, String detailedProblemDescription,
                      String repairInformation) {
        this.processed = processed;
        this.detailedProblemDescription = detailedProblemDescription;
        this.repairInformation = repairInformation;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getUserWorkOrderId() {
        return userWorkOrderId;
    }

    public void setUserWorkOrderId(Long userWorkOrderId) {
        this.userWorkOrderId = userWorkOrderId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getProblemCode() {
        return problemCode;
    }

    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getDetailedProblemDescription() {
        return detailedProblemDescription;
    }

    public void setDetailedProblemDescription(String detailedProblemDescription) {
        this.detailedProblemDescription = detailedProblemDescription;
    }

    public String getRepairInformation() {
        return repairInformation;
    }

    public void setRepairInformation(String repairInformation) {
        this.repairInformation = repairInformation;
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkOrder:" +
                "city='" + city + '\'' +
                ", device='" + device + '\'' +
                ", problemCode='" + problemCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", processed=" + processed +
                '.';
    }
}

