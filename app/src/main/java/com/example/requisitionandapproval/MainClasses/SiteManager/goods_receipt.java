package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.Order.place_purchase_order_Item_List;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.adapterClasses.OrderAdapter;
import com.example.requisitionandapproval.adapterClasses.place_Item_listAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.orderItemcls;
import com.example.requisitionandapproval.model.orderModel;
import com.example.requisitionandapproval.model.placedorderReqId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class goods_receipt extends AppCompatActivity {


    RecyclerView recyclerView;
    ApiClient apiClient = new ApiClient();

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_receipt);
        recyclerView = findViewById(R.id.orderItemList);
        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        final Spinner orderreqIDS = findViewById(R.id.orderreqIDS);

        getAllReqNumbers();

//        initdata();
//        initRecyclerView();
//


        orderreqIDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = new HashMap<>();

                map.put("reqID", orderreqIDS.getSelectedItem().toString());

                Call<List<getOrderedItemList>> call = endpoints.getOrderedItems(map);
                ArrayList<String> arlist = new ArrayList<>( );

                call.enqueue(new Callback<List<getOrderedItemList>>() {
                    @Override
                    public void onResponse(Call<List<getOrderedItemList>> call, Response<List<getOrderedItemList>> response) {
                        System.out.println("passssed");
                        if(response.code() ==200){
                        }
                        List<getOrderedItemList> it = response.body();
                        assert it != null;
                        orderModel[] itemcls  =  new orderModel[it.size()];
                        for(int i =0 ; i<it.size(); i++){
                            itemcls[i] =new orderModel (it.get(i).getItem_Description());
                        }
//                itemAdapter adapter= new itemAdapter(itemcls,place_purchase_order_Item_List.this);
//                recyclerView.setAdapter(adapter);

                        OrderAdapter adapter= new OrderAdapter(itemcls, goods_receipt.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<getOrderedItemList>> call, Throwable t) {
                        System.out.println("failed");

                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

//    private void initRecyclerView() {
//        OrderAdapter orderAdapter = new OrderAdapter(orderModels);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(orderAdapter);
//    }
//
//    private void initdata() {
//        orderModels = new ArrayList<>();
//        orderModels.add(new orderModel("Tokya Cementt"));
//        orderModels.add(new orderModel("Pipes"));
//        orderModels.add(new orderModel("Brikes"));
//
//    }

    public void getAllReqNumbers() {
        System.out.println("HEREE");
        Call<placedorderReqId> call = endpoints.getApprovedOrderIDs();
        System.out.println("HEREE12");

        call.enqueue(new Callback<placedorderReqId>() {
            @Override
            public void onResponse(Call <placedorderReqId> call, Response<placedorderReqId> response) {

                try {
                    placedorderReqId it = response.body();
                    System.out.println(it);

                    Spinner reqidspin = findViewById(R.id.orderreqIDS);
                    ArrayAdapter<String> reqadapter = new ArrayAdapter<String>(goods_receipt.this, android.R.layout.simple_spinner_item, it.getResult());
                    reqadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    reqidspin.setAdapter(reqadapter);

//                    for(int i=0; i<it.size(); i++){
//                        System.out.println("ITEMSCC" + it.get(i).getItemIDs());
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<placedorderReqId> call, Throwable t) {
                System.out.println("ERROR::"+ t);

            }
        });
    }

    public void orderDone(){




    }

}