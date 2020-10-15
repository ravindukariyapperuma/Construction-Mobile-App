package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EmployeeDashboard extends AppCompatActivity {
ImageButton addItem;
Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String un = intent.getStringExtra("username");
                String uid = intent.getStringExtra("userID");
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("username",un);
                intent1.putExtra("userID",uid);
                startActivity(intent1);
            }
        });
    }
}