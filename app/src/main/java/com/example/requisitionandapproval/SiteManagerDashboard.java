package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;

public class SiteManagerDashboard extends AppCompatActivity {
    Button approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manager_dashboard);

        final Intent intent = getIntent();
        String un = intent.getStringExtra("EXTRA_SESSION_ID");

        approve = findViewById(R.id.approve);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent(SiteManagerDashboard.this, Approve_Requisition.class);
                startActivity(intent1);
            }
        });


    }
}