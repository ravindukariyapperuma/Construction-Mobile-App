package com.example.requisitionandapproval.MainClasses.Suppliers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.adapterClasses.InprogressAdaptor;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.InprogressResponseModel;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.inprogressItemIDModel;
import com.example.requisitionandapproval.model.orderItemcls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class inprogressItemsInsupplier extends AppCompatActivity {

    Spinner inprogressreqIDS;
    RecyclerView inprogressItem;
    List<orderItemcls> orderItemcls;
   // inprogressItemIDModel inprogressItemIDModel;

    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress_items_insupplier);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        inprogressItem = findViewById(R.id.inprogressItem);
        inprogressreqIDS = findViewById(R.id.inprogressreqIDS);

        getAllReqNumbers();

        inprogressreqIDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getdetails_from_reqID(inprogressreqIDS.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getAllReqNumbers() {

        Call<inprogressItemIDModel> call = endpoints.getallInprogressIDs();
        call.enqueue(new Callback<inprogressItemIDModel>() {
            @Override
            public void onResponse(Call<inprogressItemIDModel> call, Response<inprogressItemIDModel> response) {
                try {
                    inprogressItemIDModel it = response.body();


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(inprogressItemsInsupplier.this, android.R.layout.simple_spinner_item, it.getResult());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    inprogressreqIDS.setAdapter(adapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<inprogressItemIDModel> call, Throwable t) {
                System.out.println("ERROR::" + t);
            }
        });
    }

    public void getdetails_from_reqID(final String reqID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", reqID);


        Call<List<InprogressResponseModel>> call = endpoints.getItemsDetailsByReqIDSupplier(map);

        call.enqueue(new Callback<List<InprogressResponseModel>>() {
            @Override
            public void onResponse(Call<List<InprogressResponseModel>> call, Response<List<InprogressResponseModel>> response) {
                if (response.code() == 200) {
                    Toast.makeText(inprogressItemsInsupplier.this, response.message(), Toast.LENGTH_LONG).show();
                }
                orderItemcls = new ArrayList<>();

                try {
                    List<InprogressResponseModel> it = response.body();

                    for (int i = 0; i < it.size(); i++) {
                        orderItemcls.add(new orderItemcls(it.get(i).getItemDescription(), it.get(i).getItemQty(), it.get(i).getItemPrice()));



                    }

                    initRecyclerView();
                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("123123123123");

                }
            }

            @Override
            public void onFailure(Call<List<InprogressResponseModel>> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(inprogressItemsInsupplier.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView() {
        InprogressAdaptor approveAdapter = new InprogressAdaptor(orderItemcls);
        inprogressItem.setLayoutManager(new LinearLayoutManager(this));
        inprogressItem.setAdapter(approveAdapter);
    }
}