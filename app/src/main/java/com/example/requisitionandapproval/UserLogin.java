package com.example.requisitionandapproval;

import android.os.Bundle;

import com.example.requisitionandapproval.model.userLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class UserLogin extends AppCompatActivity {

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = "http://10.0.2.2:3000";
    Button login;
    TextView username;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_login);
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);
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

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("password", password.getText().toString());

                Call<userLogin> call = endpoints.userLogin(map);
                call.enqueue(new Callback<userLogin>() {
                    @Override
                    public void onResponse(Call<userLogin> call, Response<userLogin> response) {
                        if(response.code() ==200){
                            Toast.makeText(UserLogin.this, response.message(), Toast.LENGTH_LONG).show();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String nme = jsonObject.getString("userType");
                            if(nme.equals("supplier")){
                                System.out.println("Supplier Login");
                            }else if(nme.equals("sitemanager")){
                                System.out.println("sitemanager Login");

                            }else if(nme.equals("other")){
                                System.out.println("other");

                            }else {
                                Toast.makeText(UserLogin.this, "Invlaid Login", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<userLogin> call, Throwable t) {
                        System.out.println("failed" +t);
                        Toast.makeText(UserLogin.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }

                });

            }
        });
    }
}