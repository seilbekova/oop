package model;

import java.math.BigDecimal;

public interface PricedItem {
    // 1. Абстрактный метод (требование)
    BigDecimal getPrice();

    // 2. Default метод (требование)
    default BigDecimal getPriceWithTax(BigDecimal taxRate) {
        return getPrice().multiply(BigDecimal.ONE.add(taxRate));
    }

    // 3. Static метод (требование)
    static String getCurrency() {
        return "USD";
    }
}