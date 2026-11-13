package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private SimpleChartView chartView;
    private TextView tvCurrentRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        chartView = findViewById(R.id.chartView);
        tvCurrentRate = findViewById(R.id.tvCurrentRate);
        Button btnUpdate = findViewById(R.id.btnUpdateRate);

        // Строим график при открытии
        buildChart();

        // Кнопка обновления
        btnUpdate.setOnClickListener(v -> {
            buildChart();
            Toast.makeText(this, "Данные обновлены", Toast.LENGTH_SHORT).show();
        });
    }

    private void buildChart() {
        List<Order> orders = OrderManager.getInstance().getOrders();

        if (orders.isEmpty()) {
            tvCurrentRate.setText("Выручка: 0 ₽ (нет заказов)");
            chartView.updateData(new float[]{0, 0, 0, 0, 0, 0, 0});
            return;
        }

        double total = 0;
        for (Order o : orders) {
            total += o.getTotalPrice();
        }

        float[] data = new float[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            data[i] = (float) orders.get(i).getTotalPrice();
        }

        // Если заказ всего один, добавим 0 для красоты графика
        if (data.length == 1) {
            data = new float[]{0, data[0]};
        }

        chartView.updateData(data);
        tvCurrentRate.setText("Общая выручка: " + total + " ₽");
    }
}