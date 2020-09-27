package com.example.requisitionandapproval.MainClasses.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.userLogin;
import com.example.requisitionandapproval.model.userRegisterModel;
import com.example.requisitionandapproval.place_Purchase_order;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);
        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);
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
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("PAAAAAAAAS");

                    Toast.makeText(registerUsers.this, response.message(), Toast.LENGTH_LONG).show();


                    System.out.println("adawqq"+response.code() );
                    if(response.code() == 404){
                        Toast.makeText(registerUsers.this, "Please fill all required fields!", Toast.LENGTH_LONG).show();

                    }else {
                        Intent intent = new Intent(registerUsers.this, UserLogin.class);
                        startActivity(intent);
                    }
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