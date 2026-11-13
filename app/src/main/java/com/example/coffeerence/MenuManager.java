package com.example.coffeerence;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private static MenuManager instance;
    private final List<Product> products;
    private int globalDiscountPercent = 0;

    private MenuManager() {
        products = new ArrayList<>();
        loadDefaultMenu();
    }

    public static synchronized MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    private void loadDefaultMenu() {
        // 1. ЛАТТЕ
        products.add(new Product(1, "Латте",
                "Нежный и бархатистый. Идеальный баланс крепкого эспрессо и шелковистого молока. Согревает с первых секунд и дарит чувство уюта.",
                250.0, "https://images.unsplash.com/photo-1570968915860-54d5c301fa9f?w=500",
                "Эспрессо, цельное молоко, молочная пенка"));

        // 2. КАПУЧИНО
        products.add(new Product(2, "Капучино",
                "Классика, которая никогда не подведет. Густая, воздушная пенка и насыщенный вкус. Идеально для бодрого утра и хорошего дня.",
                200.0, "https://images.unsplash.com/photo-1534778101976-62847782c213?w=500",
                "Эспрессо, молоко, плотная молочная пена"));

        // 3. АМЕРИКАНО
        products.add(new Product(3, "Американо",
                "Для тех, кто ценит чистый вкус. Насыщенный, бодрящий и невероятно ароматный черный кофе без лишних добавок. Заряд энергии гарантирован.",
                150.0, "https://images.unsplash.com/photo-1551030173-122aabc4489c?w=500",
                "Двойной эспрессо, горячая вода"));

        // 4. РАФ
        products.add(new Product(4, "Раф",
                "Сливочное облако в чашке. Нежный, сладкий и невероятно мягкий. Этот вкус невозможно забыть, а послевкусие остается надолго.",
                280.0, "https://images.unsplash.com/photo-1593443320739-77f74939d0da?w=500",
                "Эспрессо, сливки 11%, сахарная пудра, ванильный сахар"));

        // 5. ФЛЭТ УАЙТ
        products.add(new Product(5, "Флэт Уайт",
                "Двойная порция кофеина для настоящих гурманов. Крепкий, но при этом удивительно бархатистый. Для тех, кто знает толк в кофе.",
                260.0, "https://images.unsplash.com/photo-1577968897966-3d4325b36b61?w=500",
                "Двойной эспрессо, молоко, тонкий слой пенки"));

        // 6. КРУАССАН
        products.add(new Product(6, "Круассан",
                "Хрустящая корочка, нежнейшая слоеная текстура и аромат свежего масла. Идеальная пара к вашему кофе. Тает во рту.",
                180.0, "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=500",
                "Пшеничная мука, сливочное масло, миндаль, сахарная пудра"));

        // 7. ТИРАМИСУ
        products.add(new Product(7, "Тирамису",
                "Итальянский десерт с характером. Пропитанное кофе печенье, воздушный крем маскарпоне и легкая горчинка какао. Настоящее наслаждение.",
                320.0, "https://images.unsplash.com/photo-1571115177098-24ec42ed204d?w=500",
                "Печенье савоярди, сыр маскарпоне, эспрессо, какао, яйца, сахар"));

        // 8. ЧИЗКЕЙК
        products.add(new Product(8, "Чизкейк",
                "Нежный и сливочный. Тает во рту, оставляя после себя долгое послевкусие ванили и свежего сыра. Идеальное завершение дня.",
                300.0, "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=500",
                "Сыр Филадельфия, песочная основа, ваниль, сливки, сахар"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void updateProductPrice(int productId, double newPrice) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId() == productId) {
                products.set(i, new Product(p.getId(), p.getName(), p.getDescription(), newPrice, p.getImageUrl(), p.getIngredients()));
                break;
            }
        }
    }

    public int getGlobalDiscountPercent() {
        return globalDiscountPercent;
    }

    public void setGlobalDiscountPercent(int percent) {
        this.globalDiscountPercent = percent;
    }
}