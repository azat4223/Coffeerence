package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private TextView tvCartSummary;
    private TextView tvBonusInfo;
    private EditText etPromoCode;
    private CheckBox cbUseBonuses;
    private int currentPromoDiscount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvCartSummary = findViewById(R.id.tvCartSummary);
        tvBonusInfo = findViewById(R.id.tvBonusInfo);
        etPromoCode = findViewById(R.id.etPromoCode);
        cbUseBonuses = findViewById(R.id.cbUseBonuses);
        Button btnApplyPromo = findViewById(R.id.btnApplyPromo);
        Button btnGoToPayment = findViewById(R.id.btnGoToPayment);

        showCartSummary();
        showBonusInfo();

        // Обработка кнопки "Применить промокод"
        btnApplyPromo.setOnClickListener(v -> applyPromoCode(etPromoCode.getText().toString()));

        // Обработка кнопки "Перейти к оплате"
        btnGoToPayment.setOnClickListener(v -> {
            if (CartManager.getInstance().getItems().isEmpty()) {
                Toast.makeText(this, "Корзина пуста!", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = CartManager.getInstance().getTotalPrice();
            if (currentPromoDiscount > 0) {
                total = total - (total * currentPromoDiscount / 100.0);
            }

            // Переход на экран оплаты (передаем сумму и флаг бонусов)
            android.content.Intent intent = new android.content.Intent(this, PaymentActivity.class);
            intent.putExtra("total", total);
            intent.putExtra("use_bonuses", cbUseBonuses.isChecked());
            startActivity(intent);
        });
    }

    private void showCartSummary() {
        List<Product> items = CartManager.getInstance().getItems();
        if (items.isEmpty()) {
            tvCartSummary.setText("Корзина пуста");
            return;
        }

        StringBuilder summary = new StringBuilder();
        for (Product item : items) {
            summary.append(item.getName())
                    .append(" - ")
                    .append(item.getPrice())
                    .append(" ₽\n");
        }

        double total = CartManager.getInstance().getTotalPrice();
        summary.append("\nИтого: ").append(total).append(" ₽");

        if (currentPromoDiscount > 0) {
            double discount = total * currentPromoDiscount / 100.0;
            summary.append("\nПромокод ").append(currentPromoDiscount).append("%: -").append(discount).append(" ₽");
        }

        tvCartSummary.setText(summary.toString());
    }

    private void showBonusInfo() {
        tvBonusInfo.setText("Ваши бонусы: " + LoyaltyManager.getInstance().getBonusBalance());
    }

    private void applyPromoCode(String code) {
        int discount = PromoManager.getInstance().getDiscount(code);

        if (discount > 0) {
            currentPromoDiscount = discount;

            // 1. Сначала обновляем текст на экране (чтобы сумма пересчиталась)
            showCartSummary();

            // 2. Потом показываем сообщение (LENGTH_LONG, чтобы точно заметить)
            Toast.makeText(this, "Промокод применен! Скидка " + discount + "%", Toast.LENGTH_LONG).show();

        } else {
            currentPromoDiscount = 0;
            showCartSummary();
            Toast.makeText(this, "Неверный промокод", Toast.LENGTH_LONG).show();
        }
    }
}