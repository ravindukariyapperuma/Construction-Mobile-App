package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.Order.place_purchase_order_Item_List;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.DashBoards.SiteManagerDashboard;

import com.example.requisitionandapproval.model.placedorderReqId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class place_purchase_Order_List extends AppCompatActivity {

    ApiClient apiClient = new ApiClient();

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    Button ShowItemList;
    String name;
    TextView uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_purchase__order__list);

        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        uname = findViewById(R.id.uname);
        uname.setText("Site Manager : "+name );

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);
        ShowItemList = findViewById(R.id.ShowItemList);

        getAllReqNumbers();

        ShowItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner reqidspin = findViewById(R.id.reqNoSpin);
                if (reqidspin.getCount()==0){
                    Toast.makeText(place_purchase_Order_List.this,"No Item Selected",Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = new Intent(place_purchase_Order_List.this, place_purchase_order_Item_List.class);
                    intent.putExtra("orderReqId",reqidspin.getSelectedItem().toString());
                    intent.putExtra("name",name);
                    startActivity(intent);
                }

            }
        });
    }

    public void getAllReqNumbers() {
        System.out.println("HEREE");
        Call<placedorderReqId> call = endpoints.getApprovedOrderIDs();
        System.out.println("HEREE12");

        call.enqueue(new Callback <placedorderReqId>() {
            @Override
            public void onResponse(Call <placedorderReqId> call, Response <placedorderReqId> response) {

                try {
                   placedorderReqId it = response.body();
                    System.out.println(it);

                    Spinner reqidspin = findViewById(R.id.reqNoSpin);
                    ArrayAdapter<String> reqadapter = new ArrayAdapter<String>(place_purchase_Order_List.this, android.R.layout.simple_spinner_item, it.getResult());
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

    public void getReqIdByItems(){


    }

    @Override
    public void onBackPressed(){
       Intent intent = new Intent(place_purchase_Order_List.this, SiteManagerDashboard.class);
       startActivity(intent);
    }
}