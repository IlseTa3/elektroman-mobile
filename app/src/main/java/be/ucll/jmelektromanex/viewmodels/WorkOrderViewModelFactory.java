package be.ucll.jmelektromanex.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import be.ucll.jmelektromanex.repository.WorkOrderRepository;

public class WorkOrderViewModelFactory implements ViewModelProvider.Factory{

    private WorkOrderRepository repository;

    public WorkOrderViewModelFactory(WorkOrderRepository workOrderRepository){
        this.repository = workOrderRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(WorkOrderViewModel.class)){
            return (T) new WorkOrderViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
