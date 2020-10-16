package com.example.requisitionandapproval.MainClasses.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.Notification.progressBar;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registerUsers extends AppCompatActivity {
    ApiClient apiClient = new ApiClient();

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();

    Button regBtn;
    TextView username;
    TextView email;
    TextView password;
    Spinner sp;
    ImageView loginLinkArrow;
    TextView loginLink;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);
        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        loginLink = findViewById(R.id.loginLink);
        loginLinkArrow = findViewById(R.id.LoginLinkArrow);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserLogin.class);
                startActivity(intent);
            }
        });
        loginLinkArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserLogin.class);
                startActivity(intent);
            }
        });

        regBtn = findViewById(R.id.registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
    }

    public void userRegister () {

        username = findViewById(R.id.rusername);
        email = findViewById(R.id.remail);
        password = findViewById(R.id.rpassword);
        sp = findViewById(R.id.rtypes);

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username.getText().toString());
        map.put("password", password.getText().toString());
        map.put("email", email.getText().toString());
        map.put("userType", sp.getSelectedItem().toString());

        System.out.println("awd" + sp.getSelectedItem().toString());

        Call<Void> call = endpoints.userRegister(map);
        try {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, final Response<Void> response) {

                    final progressBar pbar = new progressBar(registerUsers.this);
                    new CountDownTimer(2000, 1000) {
                        public void onFinish() {
                            pbar.dismissProgress();
                            System.out.println("PAAAAAAAAS");
                            System.out.println("adawqq"+response.code() );
                            if(response.code() == 404){
                                Toast.makeText(registerUsers.this, "Please fill all required fields!", Toast.LENGTH_LONG).show();
                                new SweetAlertDialog(registerUsers.this,SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Please fill all required fields!")
                                        .show();

                            }else {

                                new SweetAlertDialog(registerUsers.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Registration Successful !")
                                        .show();
                                Intent intent = new Intent(context, UserLogin.class);
                                startActivity(intent);
                            }
                        }
                        public void onTick(long millisUntilFinished) {
                            pbar.StartLoading();

                        }
                    }.start();

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("failed" + t);
                    Toast.makeText(registerUsers.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }

            });
        } catch (Exception e) {
            Toast.makeText(registerUsers.this, "Please enter All Fields", Toast.LENGTH_LONG).show();

        }
    }


}