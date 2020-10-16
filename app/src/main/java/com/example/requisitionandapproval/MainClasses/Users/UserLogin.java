package com.example.requisitionandapproval.MainClasses.Users;

import android.content.Intent;
import android.os.Bundle;

import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.DashBoards.EmployeeDashboard;
import com.example.requisitionandapproval.DashBoards.ManagerDashBoard;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.DashBoards.SiteManagerDashboard;
import com.example.requisitionandapproval.DashBoards.SupplierDashboard;
import com.example.requisitionandapproval.model.userLogin;
import com.example.requisitionandapproval.Notification.progressBar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.example.requisitionandapproval.ApiClient.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin extends AppCompatActivity {

    ApiClient apiClient = new ApiClient();

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    Button login;
    TextView username;
    TextView password;
    ImageView registerLinkArrow;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_login);
        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        registerLink = findViewById(R.id.registerLink);
        registerLinkArrow = findViewById(R.id.RegisterLinkArrow);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),registerUsers.class);
                startActivity(intent);
            }
        });
        registerLinkArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),registerUsers.class);
                startActivity(intent);
            }
        });


        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    public void userLogin(){

        username = findViewById(R.id.tusername);
        password = findViewById(R.id.tpassword);

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username.getText().toString());
        map.put("password", password.getText().toString());

        Call<userLogin> call = endpoints.userLogin(map);
        call.enqueue(new Callback<userLogin>() {



            @Override
            public void onResponse(Call<userLogin> call, final Response<userLogin> response) {
                final progressBar pbar = new progressBar(UserLogin.this);
                new CountDownTimer(2000, 1000) {
                    public void onFinish() {
                        pbar.dismissProgress();
                        // my whole code

                        if(response.code() ==200){
                            Toast.makeText(UserLogin.this, response.message(), Toast.LENGTH_LONG).show();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String nme = jsonObject.getString("userType");
                            String userID = jsonObject.getString("userID");

                            if(nme.equals("supplier")){
                                System.out.println("Supplier Login");
                                Intent it = new Intent(getBaseContext(), SupplierDashboard.class);
                                it.putExtra("EXTRA_SESSION_ID", jsonObject.getString("username"));
                                startActivity(it);
                            }else if(nme.equals("sitemanager")){
                                Intent it = new Intent(getBaseContext(), SiteManagerDashboard.class);
                                it.putExtra("EXTRA_SESSION_ID", jsonObject.getString("username"));
                                startActivity(it);

                                System.out.println("sitemanager Login");

                            }else if(nme.equals("employee")){
                                Intent it = new Intent(UserLogin.this, EmployeeDashboard.class);
                                it.putExtra("username", jsonObject.getString("username"));
                                it.putExtra("userID", jsonObject.getString("userID"));
                                startActivity(it);
                                System.out.println("employee");

                            }else if(nme.equals("manager")){
                                Intent it = new Intent(UserLogin.this, ManagerDashBoard.class);
                                it.putExtra("username", jsonObject.getString("username"));
                                it.putExtra("userID", jsonObject.getString("userID"));
                                startActivity(it);
                                System.out.println("manager");

                            }else {
                                Toast.makeText(UserLogin.this, "Invlaid Login", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    public void onTick(long millisUntilFinished) {
                        pbar.StartLoading();

                    }
                }.start();

            }

            @Override
            public void onFailure(Call<userLogin> call, Throwable t) {
                System.out.println("failed" +t);
                Toast.makeText(UserLogin.this, "Please enter All Fields", Toast.LENGTH_LONG).show();

            }

        });

    }
}