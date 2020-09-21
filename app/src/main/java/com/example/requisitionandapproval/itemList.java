package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class itemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Itemcls[] itemcls = new Itemcls[]{
                new Itemcls("Tokyo Cement","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Pipes","10","750.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Bricks","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete),
                new Itemcls("Tokyo Cement","10","1250.00/= (PER)", R.drawable.edit, R.drawable.delete)


        };
        itemAdapter adapter= new itemAdapter(itemcls,itemList.this);
        recyclerView.setAdapter(adapter);


    }
}