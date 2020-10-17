package com.example.requisitionandapproval.adapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requisitionandapproval.MainClasses.Order.place_purchase_order_Item_List;
import com.example.requisitionandapproval.MainClasses.Suppliers.SupplierProfile;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.SupplierOrderMdel;
import com.example.requisitionandapproval.model.orderItemcls;

import java.util.ArrayList;

public class supplierOrderAdaptor extends RecyclerView.Adapter<supplierOrderAdaptor.ViewHolder>{

    SupplierOrderMdel[] itemcls;
    Context context;
    public ArrayList<String> checkedItems = new ArrayList<>();
    public supplierOrderAdaptor(SupplierOrderMdel[] itemcls, SupplierProfile activity) {
        this.itemcls = itemcls;
        this.context =activity;

    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.supplier_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SupplierOrderMdel itemclsList = itemcls[position];
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
        return itemcls.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
        TextView Qty;
        TextView PriceperItem;
        ImageView edit;
        ImageView delete;
        CheckBox ordercheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.itemName);
            Qty = itemView.findViewById(R.id.qty);
            PriceperItem = itemView.findViewById(R.id.priceperItem);


            ordercheck = itemView.findViewById(R.id.itmcheck);

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
