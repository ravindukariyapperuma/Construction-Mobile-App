package com.example.requisitionandapproval.adapterClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.ApproveModel;

import java.util.List;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveAdapter.ViewHolder>{

//    ApproveModel[] approveModels;
//    Context context;
    List<ApproveModel> approveModels1;



    public ApproveAdapter( List<ApproveModel> approveModels1) {

        this.approveModels1 = approveModels1;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       ApproveModel approveModel =approveModels1.get(position);
        holder.ItemName.setText(approveModel.getITName());
        holder.qty.setText(approveModel.getQty());
        holder.price.setText(approveModel.getPrice());

        boolean isExpanded = approveModels1.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.down.setVisibility(isExpanded ? View.GONE:View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if(approveModels1 == null ){
            return 0;
        }else{
            return approveModels1.size();

        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
        EditText qty, price;
        Button down;

        ConstraintLayout expandableLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.titleTextView);
            qty = itemView.findViewById(R.id.QTY);
            price = itemView.findViewById(R.id.PRICE);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            down = itemView.findViewById(R.id.arrowdown);

//            Qty = itemView.findViewById(R.id.qty);
//            PriceperItem = itemView.findViewById(R.id.priceperItem);
//            edit = itemView.findViewById(R.id.editBtn);
//            delete = itemView.findViewById(R.id.deleteBtn);

            ItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ApproveModel approveModel = approveModels1.get(getAdapterPosition());
                    approveModel.setExpanded(!approveModel.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });


        }
    }


}
