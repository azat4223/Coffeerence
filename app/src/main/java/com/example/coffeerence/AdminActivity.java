package com.example.coffeerence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnAnalytics = findViewById(R.id.btnAnalytics);
        Button btnManageMenu = findViewById(R.id.btnManageMenu);
        Button btnCreatePromo = findViewById(R.id.btnCreatePromo);

        // 1. Управление меню и ценами
        btnManageMenu.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageMenuActivity.class));
        });

        // 2. Аналитика
        btnAnalytics.setOnClickListener(v -> {
            startActivity(new Intent(this, AnalyticsActivity.class));
        });

        // 3. Создание промокода
        btnCreatePromo.setOnClickListener(v -> {
            startActivity(new Intent(this, CreatePromoActivity.class));
        });
    }
}