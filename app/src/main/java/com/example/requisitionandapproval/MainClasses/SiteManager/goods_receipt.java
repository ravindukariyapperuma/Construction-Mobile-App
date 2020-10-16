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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.OrderAdapter;
import com.example.requisitionandapproval.Notification.dialog;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.orderModel;
import com.example.requisitionandapproval.model.placedorderReqId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class goods_receipt extends AppCompatActivity {


    RecyclerView recyclerView;
    ApiClient apiClient = new ApiClient();
    OrderAdapter adapter ;
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    String name;
    TextView uname;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_receipt);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uname = findViewById(R.id.uname);
        uname.setText("Site Manager : "+name );

        recyclerView = findViewById(R.id.orderItemList);
        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        final Spinner orderreqIDS = findViewById(R.id.orderreqIDS);

        getAllReqNumbers();

        orderreqIDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getitems();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        orderDone();

    }

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

        Button RecivedItem = findViewById(R.id.RecivedItem);
        final Spinner orderreqIDS = findViewById(R.id.orderreqIDS);

        RecivedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] itmarr = new String[adapter.checkedItems.size()];
                for (int i = 0 ; i< adapter.checkedItems.size(); i++){

                    itmarr[i]= adapter.checkedItems.get(i).toString();
                }
                System.out.println(itmarr);

                String[] reqarr ={orderreqIDS.getSelectedItem().toString()};

                HashMap <String, String[]> map = new HashMap<>();
                map.put("reqID",reqarr );
               map.put("itemDescription", itmarr);

                Call<Void> call = endpoints.orderDone(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println(response.body());

                        getitems();

                        new SweetAlertDialog(goods_receipt.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Item Received Successful")
                                .show();

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("fail");
                    }
                });
            }
        });



    }
    public void getitems(){

        HashMap<String, String> map = new HashMap<>();
        final Spinner orderreqIDS = findViewById(R.id.orderreqIDS);
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


                adapter= new OrderAdapter(itemcls, goods_receipt.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<getOrderedItemList>> call, Throwable t) {
                System.out.println("failed");

            }

        });



    }

    public void dialog(){
        dialog di = new dialog();
        di.show(getSupportFragmentManager(),"Dialog");
    }

}