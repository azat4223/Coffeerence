package com.example.coffeerence;

import java.util.HashMap;
import java.util.Map;

public class PromoManager {
    private static PromoManager instance;
    private final Map<String, Integer> promoCodes;

    private PromoManager() {
        promoCodes = new HashMap<>();
        promoCodes.put("COFFEE10", 10);
    }

    public static synchronized PromoManager getInstance() {
        if (instance == null) {
            instance = new PromoManager();
        }
        return instance;
    }

    public void addPromoCode(String code, int discountPercent) {
        promoCodes.put(code.toUpperCase(), discountPercent);
    }

    public int getDiscount(String code) {
        if (code == null) return 0;
        Integer discount = promoCodes.get(code.toUpperCase());
        return discount != null ? discount : 0;
    }
}