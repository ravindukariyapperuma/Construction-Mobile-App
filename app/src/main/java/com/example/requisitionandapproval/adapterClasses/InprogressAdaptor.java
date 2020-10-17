package com.example.requisitionandapproval.adapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requisitionandapproval.MainClasses.Order.place_purchase_order_Item_List;
import com.example.requisitionandapproval.MainClasses.Suppliers.inprogressItemsInsupplier;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.orderItemcls;

import java.util.List;

public class InprogressAdaptor extends RecyclerView.Adapter<InprogressAdaptor.ViewHolder> {

    List<orderItemcls> itemcls;
    Context context;

    public InprogressAdaptor( List<orderItemcls> itemcls) {
        this.itemcls = itemcls;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_purchase_order_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final orderItemcls itemclsList = itemcls.get(position);
        holder.ItemName.setText(itemclsList.getItName());
        holder.Qty.setText(itemclsList.getQty());
        holder.PriceperItem.setText(itemclsList.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,itemclsList.getItName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemcls == null ){
            return 0;
        }else{
            return itemcls.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
        TextView Qty;
        TextView PriceperItem;
        ImageView edit;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.placeitemName);
            Qty = itemView.findViewById(R.id.placeqty);
            PriceperItem = itemView.findViewById(R.id.placepriceperItem);


        }
    }

}
