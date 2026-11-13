package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private LinearLayout ordersContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ordersContainer = findViewById(R.id.ordersContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOrdersList();
    }

    private void updateOrdersList() {
        ordersContainer.removeAllViews();
        List<Order> orders = OrderManager.getInstance().getOrders();

        if (orders.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("Заказов нет");
            ordersContainer.addView(tv);
            return;
        }

        for (Order order : orders) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 16, 16, 16);
            card.setBackgroundColor(0xFFEEEEEE);

            StringBuilder itemsText = new StringBuilder("Состав: ");
            List<Product> items = order.getItems();
            if (items != null && !items.isEmpty()) {
                for (int i = 0; i < items.size(); i++) {
                    itemsText.append(items.get(i).getName());
                    if (i < items.size() - 1) itemsText.append(", ");
                }
            } else {
                itemsText.append("(нет данных)");
            }

            TextView tvInfo = new TextView(this);
            tvInfo.setText("Заказ #" + order.getId() +
                    "\nСтатус: " + order.getStatus() +
                    "\n" + itemsText +
                    "\nСумма: " + order.getTotalPrice() + " ₽");
            tvInfo.setTextSize(16);
            card.addView(tvInfo);

            Button btnChange = new Button(this);
            btnChange.setText("Изменить статус");
            btnChange.setOnClickListener(v -> {
                if (order.getStatus().equals("В обработке")) {
                    order.setStatus("Готовится");
                } else if (order.getStatus().equals("Готовится")) {
                    OrderManager.getInstance().removeOrder(order);
                } else {
                    order.setStatus("В обработке");
                }
                updateOrdersList();
            });
            card.addView(btnChange);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            ordersContainer.addView(card);
        }
    }
}