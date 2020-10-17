package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.requisitionandapproval.MainClasses.Managers.ManagerApprove;
import com.example.requisitionandapproval.R;

public class ManagerDashBoard extends AppCompatActivity {

    String username;
    ImageButton Goods ;
    TextView txtManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dash_board);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        Goods= findViewById(R.id.Goods);
        Goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerDashBoard.this, ManagerApprove.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        txtManager = findViewById(R.id.txtManager);
        txtManager.setText(username);
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