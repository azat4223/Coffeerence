package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

public class CreatePromoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_promo);

        EditText etCode = findViewById(R.id.etNewPromoCode);
        EditText etPercent = findViewById(R.id.etDiscountPercent);
        Button btnSave = findViewById(R.id.btnSavePromo);

        btnSave.setOnClickListener(v -> {
            String code = etCode.getText().toString().trim().toUpperCase();
            String percentStr = etPercent.getText().toString().trim();
            if (code.isEmpty() || percentStr.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                return;
            }
            int percent;
            try {
                percent = Integer.parseInt(percentStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный формат скидки", Toast.LENGTH_SHORT).show();
                return;
            }
            PromoManager.getInstance().addPromoCode(code, percent);
            Toast.makeText(this, "Промокод " + code + " создан!", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}