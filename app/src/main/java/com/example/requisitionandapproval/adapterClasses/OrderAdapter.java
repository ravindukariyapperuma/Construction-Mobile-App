package com.example.requisitionandapproval.adapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requisitionandapproval.MainClasses.SiteManager.goods_receipt;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.orderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    orderModel[] orderModels;
    Context context;
    public OrderAdapter( orderModel[] orderModels, goods_receipt activity) {

        this.orderModels = orderModels;
        this.context = activity;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        orderModel orderModel =orderModels[position];
        holder.ItemName.setText(orderModel.getITName());
//        holder.qty.setText(orderModel.getQty());
//        holder.price.setText(orderModel.getPrice());

//        boolean isExpanded = approveModels1.get(position).isExpanded();
//        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//        holder.down.setVisibility(isExpanded ? View.GONE:View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return orderModels.length;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
//        EditText qty, price;
//        Button down;

        ConstraintLayout expandableLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.titleTextView);
//            qty = itemView.findViewById(R.id.QTY);
//            price = itemView.findViewById(R.id.PRICE);
//            expandableLayout = itemView.findViewById(R.id.expandableLayout);
//            down = itemView.findViewById(R.id.arrowdown);

//            Qty = itemView.findViewById(R.id.qty);
//            PriceperItem = itemView.findViewById(R.id.priceperItem);
//            edit = itemView.findViewById(R.id.editBtn);
//            delete = itemView.findViewById(R.id.deleteBtn);

//            ItemName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                    orderModels approveModel = orderModels.get(getAdapterPosition());
//                    approveModel.setExpanded(!approveModel.isExpanded());
//                    notifyItemChanged(getAdapterPosition());
//
//                }
//            });


        }
    }


}
