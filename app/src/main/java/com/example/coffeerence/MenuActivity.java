package com.example.coffeerence;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeapp.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private final List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerViewMenu);
        Button btnGoToCart = findViewById(R.id.btnGoToCart);
        Button btnLoyaltyCard = findViewById(R.id.btnLoyaltyCard);
        Button btnMyOrders = findViewById(R.id.btnMyOrders);
        Button btnBackToRoles = findViewById(R.id.btnBackToRoles);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(productList, product -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("desc", product.getDescription());
            intent.putExtra("ingredients", product.getIngredients()); // Передаем состав
            intent.putExtra("price", product.getPrice());
            intent.putExtra("imageUrl", product.getImageUrl());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        btnGoToCart.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));
        btnLoyaltyCard.setOnClickListener(v -> startActivity(new Intent(this, LoyaltyActivity.class)));
        btnMyOrders.setOnClickListener(v -> showMyOrders());
        btnBackToRoles.setOnClickListener(v -> finish());

        loadProducts();
    }

    private void loadProducts() {
        productList.clear();
        List<Product> baseProducts = MenuManager.getInstance().getProducts();
        int discount = MenuManager.getInstance().getGlobalDiscountPercent();

        for (Product p : baseProducts) {
            double finalPrice = p.getPrice();
            if (discount > 0) {
                finalPrice = finalPrice - (finalPrice * discount / 100.0);
            }
            productList.add(new Product(p.getId(), p.getName(), p.getDescription(), finalPrice, p.getImageUrl(), p.getIngredients()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void showMyOrders() {
        List<Order> orders = OrderManager.getInstance().getOrders();
        if (orders.isEmpty()) {
            Toast.makeText(this, "У вас нет заказов", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder sb = new StringBuilder("Ваши заказы:\n");
        for (Order o : orders) {
            sb.append("Заказ #").append(o.getId()).append(": ").append(o.getStatus()).append("\n");
        }
        new AlertDialog.Builder(this)
                .setTitle("Статус заказов")
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}