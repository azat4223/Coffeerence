package com.example.coffeerence;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;


public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        // Кнопка Клиента
        findViewById(R.id.btnRoleClient).setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Кнопка Сотрудника
        findViewById(R.id.btnRoleEmployee).setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, EmployeeActivity.class);
            startActivity(intent);
        });

        // Кнопка Админа
        findViewById(R.id.btnRoleAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(RoleSelectionActivity.this, AdminActivity.class);
            startActivity(intent);
        });
    }
}