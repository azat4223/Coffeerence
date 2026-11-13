package com.example.coffeerence;

public class LoyaltyManager {
    private static LoyaltyManager instance;
    private int bonusBalance;

    private LoyaltyManager() {
        // Начальный баланс (например, у нового пользователя 0)
        bonusBalance = 1500; // Оставим 1500 для теста
    }

    public static synchronized LoyaltyManager getInstance() {
        if (instance == null) {
            instance = new LoyaltyManager();
        }
        return instance;
    }

    public int getBonusBalance() {
        return bonusBalance;
    }

    // Метод для начисления бонусов
    public void addBonus(double spentAmount) {
        // Начисляем 1 бонус за каждые 10 рублей
        int earned = (int) (spentAmount / 10);
        bonusBalance += earned;
    }
}