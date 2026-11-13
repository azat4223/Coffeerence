package com.example.coffeerence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.coffeeapp.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Получаем данные из MenuActivity
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");
        String ingredients = getIntent().getStringExtra("ingredients");
        double price = getIntent().getDoubleExtra("price", 0);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDesc = findViewById(R.id.tvDetailDescription);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        Button btnAdd = findViewById(R.id.btnAddToCart);

        Glide.with(this).load(imageUrl).into(ivImage);
        tvName.setText(name);
        tvPrice.setText(price + " ₽");
        tvDesc.setText(description);
        tvIngredients.setText(ingredients);

        btnAdd.setOnClickListener(v -> {
            // Создаем продукт для корзины, передавая ИМЯ и СОСТАВ
            Product product = new Product(0, name, description, price, imageUrl, ingredients);
            CartManager.getInstance().addProduct(product);
            Toast.makeText(this, "Добавлено: " + name, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}