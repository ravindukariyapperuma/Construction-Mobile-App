package com.example.requisitionandapproval.MainClasses.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.Items.itemList;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.itemAdapter;
import com.example.requisitionandapproval.adapterClasses.place_Item_listAdapter;
import com.example.requisitionandapproval.model.ItemResult;
import com.example.requisitionandapproval.model.Itemcls;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.orderItemcls;
import com.example.requisitionandapproval.progressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class place_purchase_order_Item_List extends AppCompatActivity {
    ApiClient apiClient = new ApiClient();

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    Context context = this;
    String name;
    TextView uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_purchase_order__item__list);

        Intent intent1 = getIntent();
        name= intent1.getStringExtra("name");
        uname = findViewById(R.id.uname);
        uname.setText("Site Manager : "+name );


        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

       final RecyclerView recyclerView = findViewById(R.id.plasePurchaseOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderReqId");
        HashMap<String, String> map = new HashMap<>();

        map.put("reqID", orderId);

        Call<List<getOrderedItemList>> call = endpoints.getOrderedItems(map);
        ArrayList<String> arlist = new ArrayList<>( );

        call.enqueue(new Callback<List<getOrderedItemList>>() {
            @Override
            public void onResponse(Call<List<getOrderedItemList>> call, final Response<List<getOrderedItemList>> response) {






                        System.out.println("passssed");
                        if(response.code() ==200){
                        }
                        List<getOrderedItemList> it = response.body();
                        assert it != null;
                        orderItemcls[] itemcls  =  new orderItemcls[it.size()];
                        for(int i =0 ; i<it.size(); i++){
                            itemcls[i] =new orderItemcls (it.get(i).getItem_Description(), it.get(i).getItem_Quantity(), it.get(i).getItem_AgreedPrice());
                        }
//                itemAdapter adapter= new itemAdapter(itemcls,place_purchase_order_Item_List.this);
//                recyclerView.setAdapter(adapter);

                        place_Item_listAdapter adapter= new place_Item_listAdapter(itemcls,place_purchase_order_Item_List.this);
                        recyclerView.setAdapter(adapter);











            }

            @Override
            public void onFailure(Call<List<getOrderedItemList>> call, Throwable t) {
                System.out.println("failed");

            }

        });




    }


}