package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqNumbers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Approve_Requisition extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    private Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve__requisition);

        recyclerView = findViewById(R.id.approveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getAllReqNumbers();

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

    public void getAllReqNumbers() {
        System.out.println("HEREE");
        Call<List<GetReqNumbers>> call = endpoints.getAllReqNumbers();
        System.out.println("HEREE12");
        call.enqueue(new Callback<List<GetReqNumbers>>() {
            @Override
            public void onResponse(Call <List<GetReqNumbers>> call, Response<List<GetReqNumbers>> response) {
                try {
                    List<GetReqNumbers> it = response.body();
                    String[] arraySpinner = new String[it.size()];
                    for(int i=0; i<it.size(); i++){

                        arraySpinner[i]= it.get(i).getItemIDs();
                    }
                    Spinner s = (Spinner) findViewById(R.id.reqIDS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Approve_Requisition.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<List<GetReqNumbers>> call, Throwable t) {
                System.out.println("ERROR::"+ t);
            }
        });
    }

}