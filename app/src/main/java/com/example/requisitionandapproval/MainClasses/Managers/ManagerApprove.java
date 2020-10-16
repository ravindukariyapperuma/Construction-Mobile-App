package com.example.requisitionandapproval.MainClasses.Managers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.MainClasses.Users.UserLogin;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.ManagerReqNumbers;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.getDetaislByManagerReqID;
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

public class ManagerApprove extends AppCompatActivity {
    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    ReqApprovalModel[] rm;
    static int val;
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_approve);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        TextView uname = findViewById(R.id.uname);
        uname.setText("Manager : "+username);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        recyclerView = findViewById(R.id.ManagerapproveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllReqNumbers();

        final Spinner reqId = (Spinner) findViewById(R.id.ManagerreqIDS);
        reqId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String RequisitionId = reqId.getSelectedItem().toString();
                getdetails_from_reqID(RequisitionId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button add_item = findViewById(R.id.add_item);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderApprove();
            }
        });

        Button decline_Requsition = findViewById(R.id.decline_Requsition);

        decline_Requsition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                decline_Request();
            }
        });

    }

    public void getAllReqNumbers() {

        Call<List<ManagerReqNumbers>> call = endpoints.getAllManagerReqNumbers();
        call.enqueue(new Callback<List<ManagerReqNumbers>>() {
            @Override
            public void onResponse(Call<List<ManagerReqNumbers>> call, Response<List<ManagerReqNumbers>> response) {
                try {
                    List<ManagerReqNumbers> it = response.body();
                    String[] arraySpinner = new String[it.size()];
                    for (int i = 0; i < it.size(); i++) {

                        arraySpinner[i] = it.get(i).getItemIDs();
                    }
                    Spinner s = (Spinner) findViewById(R.id.ManagerreqIDS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManagerApprove.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);
                    Button decline_Requsition = findViewById(R.id.decline_Requsition);
                    Button add_item = findViewById(R.id.add_item);
                    if (arraySpinner.length==0){

                        decline_Requsition.setClickable(false);
                        add_item.setClickable(false);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ManagerReqNumbers>> call, Throwable t) {
                System.out.println("ERROR::" + t);
            }
        });
    }

    public void getdetails_from_reqID(final String reqID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", reqID);


        Call<List<getDetaislByManagerReqID>> call = endpoints.getItemsByManagerReqID(map);

        call.enqueue(new Callback<List<getDetaislByManagerReqID>>() {
            @Override
            public void onResponse(Call<List<getDetaislByManagerReqID>> call, Response<List<getDetaislByManagerReqID>> response) {
                if (response.code() == 200) {
                    Toast.makeText(ManagerApprove.this, response.message(), Toast.LENGTH_LONG).show();
                }
                approveModels = new ArrayList<>();

                try {
                    List<getDetaislByManagerReqID> it = response.body();
                    rm = new ReqApprovalModel[it.size()];
                    for (int i = 0; i < it.size(); i++) {
                        approveModels.add(new ApproveModel(it.get(i).getDes(), it.get(i).getQty(), it.get(i).getPrice()));
                        String nm = it.get(i).getPrice();
                        for (int j = 0; j < it.size(); j++) {
                            if(i == 0){
                                int itmprive = Integer.parseInt(it.get(j).getPrice());
                                int quantity = Integer.parseInt(it.get(j).getPrice());
                                val = itmprive * quantity;
                            }
                        }
                        rm[i] = new ReqApprovalModel(reqID, username,it.get(i).getDes(), it.get(i).getPrice(),it.get(i).getQty() );
                    }

                    initRecyclerView();
                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("123123123123");

                }
            }

            @Override
            public void onFailure(Call<List<getDetaislByManagerReqID>> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(ManagerApprove.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initRecyclerView() {
        ApproveAdapter approveAdapter = new ApproveAdapter(approveModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(approveAdapter);
    }

    public void orderApprove(){

        Button add_item = findViewById(R.id.add_item);
        final Spinner ManagerreqIDS = findViewById(R.id.ManagerreqIDS);




                HashMap <String, String> map = new HashMap<>();
                map.put("reqID",ManagerreqIDS.getSelectedItem().toString() );
                map.put("username",username);

                map.put("status", "APPROVED");

                Call<Void> call = endpoints.placeManagerOrder(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println(response.body());

                        final progressBar pbar = new progressBar(ManagerApprove.this);
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                pbar.dismissProgress();
                                // my whole code

                                new SweetAlertDialog(ManagerApprove.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Order Approval Successful")
                                        .show();

                            }

                            public void onTick(long millisUntilFinished) {
                                pbar.StartLoading();

                            }
                        }.start();


                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        new SweetAlertDialog(ManagerApprove.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Order Approval Unsuccessful")
                                .show();
                        System.out.println("fail");
                    }
                });




    }

    public void decline_Request(){


        Spinner decSpin = findViewById(R.id.ManagerreqIDS);

        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", decSpin.getSelectedItem().toString());


        Call<Void> call = endpoints.declinemanagerRequsition(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                final progressBar pbar = new progressBar(ManagerApprove.this);
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        pbar.dismissProgress();
                        // my whole code

                        final Spinner reqId = (Spinner) findViewById(R.id.ManagerreqIDS);
                        new SweetAlertDialog(ManagerApprove.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Order Decline Successful")
                                .show();
                        //Toast.makeText(ManagerApprove.this,"Decline Successful",Toast.LENGTH_LONG).show();
                        String RequisitionId = reqId.getSelectedItem().toString();
                        getdetails_from_reqID(RequisitionId);

                    }

                    public void onTick(long millisUntilFinished) {
                        pbar.StartLoading();

                    }
                }.start();





            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(ManagerApprove.this,"Decline Faild",Toast.LENGTH_SHORT).show();
            }
        });


    }
}