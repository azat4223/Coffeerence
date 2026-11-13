package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

import java.util.List;

public class ManageMenuActivity extends AppCompatActivity {

    private LinearLayout menuContainer;
    private EditText etGlobalDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        menuContainer = findViewById(R.id.menuContainer);
        etGlobalDiscount = findViewById(R.id.etGlobalDiscount);
        Button btnApplyDiscount = findViewById(R.id.btnApplyGlobalDiscount);

        // Показываем текущую скидку
        etGlobalDiscount.setText(String.valueOf(MenuManager.getInstance().getGlobalDiscountPercent()));

        // Кнопка применения глобальной скидки
        btnApplyDiscount.setOnClickListener(v -> {
            String discountStr = etGlobalDiscount.getText().toString();
            if (discountStr.isEmpty()) return;

            int discount = Integer.parseInt(discountStr);
            if (discount < 0 || discount > 100) {
                Toast.makeText(this, "Скидка от 0 до 100%", Toast.LENGTH_SHORT).show();
                return;
            }
            MenuManager.getInstance().setGlobalDiscountPercent(discount);
            Toast.makeText(this, "Акция " + discount + "% применена!", Toast.LENGTH_SHORT).show();
            updateMenuList();
        });

        updateMenuList();
    }

    private void updateMenuList() {
        menuContainer.removeAllViews();
        List<Product> products = MenuManager.getInstance().getProducts();
        int discount = MenuManager.getInstance().getGlobalDiscountPercent();

        for (Product p : products) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(8, 8, 8, 8);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);

            // Название и цена
            TextView tvName = new TextView(this);
            tvName.setText(p.getName());
            tvName.setTextSize(16);
            LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            tvName.setLayoutParams(paramsName);

            // Поле для изменения цены
            EditText etPrice = new EditText(this);
            etPrice.setText(String.valueOf(p.getPrice()));
            etPrice.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
            LinearLayout.LayoutParams paramsPrice = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.WRAP_CONTENT);
            etPrice.setLayoutParams(paramsPrice);

            // Кнопка сохранения цены
            Button btnSave = new Button(this);
            btnSave.setText("ОК");
            btnSave.setOnClickListener(v -> {
                try {
                    double newPrice = Double.parseDouble(etPrice.getText().toString());
                    MenuManager.getInstance().updateProductPrice(p.getId(), newPrice);
                    Toast.makeText(this, "Цена обновлена", Toast.LENGTH_SHORT).show();
                    updateMenuList();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Неверная цена", Toast.LENGTH_SHORT).show();
                }
            });

            // Если акция активна, показываем старую цену зачеркнутой
            if (discount > 0) {
                double discounted = p.getPrice() - (p.getPrice() * discount / 100.0);
                tvName.setText(p.getName() + " (" + discounted + " ₽)");
            }

            row.addView(tvName);
            row.addView(etPrice);
            row.addView(btnSave);

            menuContainer.addView(row);
        }
    }
}