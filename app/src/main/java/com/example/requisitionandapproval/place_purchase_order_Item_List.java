package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class place_purchase_order_Item_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_purchase_order__item__list);

        RecyclerView recyclerView = findViewById(R.id.plasePurchaseOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Itemcls[] itemcls = new Itemcls[]{
                new Itemcls("Tokyo Cement","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Pipes","10","750.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Bricks","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Tokyo Cement","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete)


        };
        place_Item_listAdapter adapter= new place_Item_listAdapter(itemcls,place_purchase_order_Item_List.this);
        recyclerView.setAdapter(adapter);

    }
}