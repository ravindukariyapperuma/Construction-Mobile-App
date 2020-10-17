package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.goods_receipt;
import com.example.requisitionandapproval.MainClasses.Suppliers.DeliverdItems;
import com.example.requisitionandapproval.MainClasses.Suppliers.SupplierProfile;
import com.example.requisitionandapproval.MainClasses.Suppliers.inprogressItemsInsupplier;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.SupplierAvailability;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupplierDashboard extends AppCompatActivity {

    String EXTRA_SESSION_ID;
    ImageView supplierr,Btninprogress,BtnDelivered;
    AppCompatRadioButton rbAvailable, rbNotAvailable;
    RadioButton rdActive, rdNotActive;
    TextView txtUsername;
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
//    String username ="abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_dashboard);
        rbAvailable = findViewById(R.id.rbAvailable);
        rbNotAvailable = findViewById(R.id.rbNotAvailable);
        Btninprogress = findViewById(R.id.btninprogress);
        BtnDelivered = findViewById(R.id.btnDelivered);
        txtUsername = findViewById(R.id.txtUsername);
        supplierr = findViewById(R.id.supplierr);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);



        Intent intent = getIntent();
        EXTRA_SESSION_ID = intent.getStringExtra("EXTRA_SESSION_ID");


        supplierr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SupplierDashboard.this, SupplierProfile.class);
                intent1.putExtra("username",EXTRA_SESSION_ID);
                startActivity(intent1);
            }
        });


        Btninprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, inprogressItemsInsupplier.class);
                intent.putExtra("username",EXTRA_SESSION_ID);
                startActivity(intent);
            }
        });

        BtnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, DeliverdItems.class);
                intent.putExtra("username",EXTRA_SESSION_ID);
                startActivity(intent);
            }
        });




            rbAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkAvailable(EXTRA_SESSION_ID, "available");
                }
            });
            rbNotAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkAvailable(EXTRA_SESSION_ID, "notavailable");
                }
            });

        initialAvailability(EXTRA_SESSION_ID);
        txtUsername.setText(EXTRA_SESSION_ID);


    }

    public void initialAvailability(String userName){
        HashMap<String, String> map = new HashMap<>();
        map.put("username",userName );

        Call<SupplierAvailability> call = endpoints.checkInitialAvailability(map);
        call.enqueue(new Callback<SupplierAvailability>() {
            @Override
            public void onResponse(Call<SupplierAvailability> call, Response<SupplierAvailability> response) {
                System.out.println(response.body());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String availability = jsonObject.getString("result");

                    if(availability.equals("available")){
                        rbAvailable.setChecked(true);
                    }else{
                        rbNotAvailable.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SupplierAvailability> call, Throwable t) {
                System.out.println("fail");
            }
        });
    }

    public void checkAvailable(String userName, final String status){

        HashMap<String, String> map = new HashMap<>();
        map.put("username",userName );
        map.put("status", status);

        Call<Void> call = endpoints.chechAvailable(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.body());

                if (status == "available"){
                    new SweetAlertDialog(SupplierDashboard.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("your status is AVAILABLE !")
                            .show();
                }else {

                    new SweetAlertDialog(SupplierDashboard.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("your status is UNAVAILABLE !")
                            .show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("fail");
            }
        });

    }

    public void onRadioButtonClick(View view){
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}