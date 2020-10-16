package com.example.requisitionandapproval.adapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requisitionandapproval.MainClasses.SiteManager.goods_receipt;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.orderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    orderModel[] orderModels;
    Context context;
    public ArrayList<String> checkedItems = new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return orderModels.length;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
        CheckBox ordercheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.titleTextView);
            ordercheck = itemView.findViewById(R.id.ordercheck);

            ordercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true){
                        checkedItems.add(ItemName.getText().toString());
                        Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                    }else{

                            checkedItems.remove(ItemName.getText().toString());

                        Toast.makeText(context,"Unclicked",Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(checkedItems.size());
                }
            });


        }
    }


}
