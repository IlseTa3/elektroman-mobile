package be.ucll.jmelektromanex;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.ucll.jmelektromanex.entities.WorkOrder;

public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.WorkOrderViewHolder>{

    private List<WorkOrder> workOrders = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(WorkOrder workOrder);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    public void submit(List<WorkOrder> workOrderList){
        workOrders = workOrderList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public WorkOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workorder_item, parent, false);
        return new WorkOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOrderViewHolder holder, int position) {
        WorkOrder currentWorkOrder = workOrders.get(position);
        holder.bind(currentWorkOrder);
    }

    @Override
    public int getItemCount() {
        return workOrders.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
        notifyDataSetChanged();
    }

    public class WorkOrderViewHolder extends RecyclerView.ViewHolder {
        TextView city, device, problemCode, name;
        CheckBox processed;

        public WorkOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.text_view_city);
            device = itemView.findViewById(R.id.text_view_device);
            problemCode = itemView.findViewById(R.id.text_view_problem_code);
            name = itemView.findViewById(R.id.text_view_customer_name);
            processed = itemView.findViewById(R.id.checkbox_processed);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(workOrders.get(pos));
                    }
                }
            });
        }
        public void bind(WorkOrder workOrder){
            city.setText(workOrder.getCity());
            device.setText(workOrder.getDevice());
            problemCode.setText(workOrder.getProblemCode());
            name.setText(workOrder.getCustomerName());
            processed.setChecked(workOrder.isProcessed());
            processed.setOnCheckedChangeListener((buttonView, isChecked) -> workOrder.setProcessed(isChecked));
        }
    }
}

