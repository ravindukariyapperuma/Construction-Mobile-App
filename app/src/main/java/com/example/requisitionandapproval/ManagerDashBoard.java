package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.requisitionandapproval.MainClasses.Managers.ManagerApprove;

public class ManagerDashBoard extends AppCompatActivity {

        ImageButton Goods ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dash_board);

        Goods = findViewById(R.id.Goods);
        Goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerDashBoard.this, ManagerApprove.class);
                startActivity(intent);
            }
        });
    }

}