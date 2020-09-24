package com.example.requisitionandapproval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class place_Item_listAdapter extends RecyclerView.Adapter<place_Item_listAdapter.ViewHolder> {

    Itemcls[] itemcls;
    Context context;

    public place_Item_listAdapter(Itemcls[] itemcls, place_purchase_order_Item_List activity) {
        this.itemcls = itemcls;
        this.context =activity;

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

        final Itemcls itemclsList = itemcls[position];
        holder.ItemName.setText(itemclsList.getItName());
        holder.Qty.setText(itemclsList.getQty());
        holder.PriceperItem.setText(itemclsList.getPrice());
        holder.edit.setImageResource(itemclsList.getEditbtn());
        holder.delete.setImageResource(itemclsList.getDeleteBtn());


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





        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.placeitemName);
            Qty = itemView.findViewById(R.id.placeqty);
            PriceperItem = itemView.findViewById(R.id.placepriceperItem);
            edit = itemView.findViewById(R.id.placeeditBtn);
            delete = itemView.findViewById(R.id.placedeleteBtn);


        }
    }
}
