package model;

public interface Searchable {
    // 1. Абстрактный метод (требование)
    boolean matches(String keyword);

    // 2. Default метод (требование)
    default boolean matchesIgnoreCase(String keyword) {
        return matches(keyword.toLowerCase());
    }
}