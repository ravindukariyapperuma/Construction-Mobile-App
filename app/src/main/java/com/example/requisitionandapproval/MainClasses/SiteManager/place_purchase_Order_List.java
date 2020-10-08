package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.ItemResult;

import com.example.requisitionandapproval.model.userLogin;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_purchase__order__list);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);
        ShowItemList = findViewById(R.id.ShowItemList);

        ShowItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllReqNumbers();
            }
        });
    }

    public void getAllReqNumbers() {
        System.out.println("HEREE");
        Call<List<GetReqNumbers>> call = endpoints.getAllReqNumbers();
        System.out.println("HEREE12");

        call.enqueue(new Callback <List<GetReqNumbers>>() {
            @Override
            public void onResponse(Call <List<GetReqNumbers>> call, Response <List<GetReqNumbers>> response) {

                try {
                    List<GetReqNumbers> it = response.body();
                    for(int i=0; i<it.size(); i++){
                        System.out.println("ITEMSCC" + it.get(i).getItemIDs());
                    }
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

    public void getReqIdByItems(){


    }
}