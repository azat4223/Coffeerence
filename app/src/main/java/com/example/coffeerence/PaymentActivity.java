package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        double total = getIntent().getDoubleExtra("total", 0);
        boolean useBonuses = getIntent().getBooleanExtra("use_bonuses", false);

        TextView tvFinalTotal = findViewById(R.id.tvFinalTotal);
        Button btnPay = findViewById(R.id.btnPay);

        tvFinalTotal.setText("Итого к оплате: " + total + " ₽");

        btnPay.setOnClickListener(v -> {
            if (CartManager.getInstance().getItems().isEmpty()) {
                Toast.makeText(this, "Корзина пуста!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Берем товары из корзины
            List<Product> cartItems = CartManager.getInstance().getItems();

            // 2. СОЗДАЕМ НОВЫЙ СПИСОК, ГАРАНТИРУЯ, ЧТО ИМЯ И СОСТАВ НЕ ПУСТЫЕ
            List<Product> safeItems = new ArrayList<>();
            for (Product p : cartItems) {
                String safeName = (p.getName() != null && !p.getName().isEmpty()) ? p.getName() : "Товар";
                String safeIngredients = (p.getIngredients() != null && !p.getIngredients().isEmpty()) ? p.getIngredients() : "Состав не указан";

                // Создаем новый Product с явными данными
                Product safeProduct = new Product(p.getId(), safeName, p.getDescription(), p.getPrice(), p.getImageUrl(), safeIngredients);
                safeItems.add(safeProduct);
            }

            // 3. Создаем заказ с безопасным списком
            Order newOrder = new Order(safeItems);
            OrderManager.getInstance().addOrder(newOrder);

            // 4. Бонусы (упрощенно)
            int bonusBalance = LoyaltyManager.getInstance().getBonusBalance();
            if (useBonuses) {
                if (bonusBalance >= total) {
                    LoyaltyManager.getInstance().addBonus(-total);
                } else {
                    LoyaltyManager.getInstance().addBonus(-bonusBalance);
                }
            } else {
                int earned = (int) (total / 10);
                LoyaltyManager.getInstance().addBonus(earned);
            }

            // 5. Очищаем корзину
            CartManager.getInstance().clear();
            finishAffinity();
        });
    }
}