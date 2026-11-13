package com.example.coffeerence;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

public class LoyaltyActivity extends AppCompatActivity {

    private TextView tvBonusBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty);

        tvBonusBalance = findViewById(R.id.tvBonusBalance);

        // Берем актуальный баланс из менеджера и ставим его на экран
        int currentBalance = LoyaltyManager.getInstance().getBonusBalance();
        tvBonusBalance.setText(currentBalance + " баллов");
    }
}