package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Approve_Requisition extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve__requisition);

         recyclerView = findViewById(R.id.approveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ApproveModel[] approveModels = new ApproveModel[]{
//
//                new ApproveModel("Tokyo cement"),
//                new ApproveModel("Pipes")
//
//
//
//        };
//        ApproveAdapter adapter= new ApproveAdapter(approveModels,Approve_Requisition.this);
//        recyclerView.setAdapter(adapter);
        initdata();
        initRecyclerView();
    }
    private void initRecyclerView() {
        ApproveAdapter approveAdapter = new ApproveAdapter(approveModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(approveAdapter);
    }
    private void initdata(){
        approveModels = new ArrayList<>();
        approveModels.add(new ApproveModel("Tokya Cementt"));
        approveModels.add(new ApproveModel("Pipes"));
        approveModels.add(new ApproveModel("Brikes"));

    }
}