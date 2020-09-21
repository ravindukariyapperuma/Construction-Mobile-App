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

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> {

    Itemcls[] itemcls;
    Context context;

    public itemAdapter(Itemcls[] itemcls, itemList activity) {
       this.itemcls = itemcls;
       this.context =activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_lists,parent,false);
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

            ItemName = itemView.findViewById(R.id.itemName);
            Qty = itemView.findViewById(R.id.qty);
            PriceperItem = itemView.findViewById(R.id.priceperItem);
            edit = itemView.findViewById(R.id.editBtn);
            delete = itemView.findViewById(R.id.deleteBtn);


        }
    }
}
