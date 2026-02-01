package utils;

import model.Book;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class SortingUtils {

    // Лямбда-выражения для сортировки книг (требование задания)

    // 1. Сортировка по цене (лямбда)
    public static void sortBooksByPrice(List<Book> books, boolean ascending) {
        // Лямбда-выражение как Comparator
        Comparator<Book> priceComparator = (book1, book2) -> {
            return book1.getPrice().compareTo(book2.getPrice());
        };

        if (!ascending) {
            priceComparator = priceComparator.reversed();
        }

        books.sort(priceComparator);
    }

    // 2. Сортировка по году (лямбда)
    public static void sortBooksByYear(List<Book> books, boolean ascending) {
        // Лямбда-выражение напрямую в sort()
        books.sort((b1, b2) -> {
            int comparison = Integer.compare(b1.getYear(), b2.getYear());
            return ascending ? comparison : -comparison;
        });
    }

    // 3. Сортировка по названию (лямбда)
    public static void sortBooksByTitle(List<Book> books, boolean ascending) {
        // Лямбда с использованием Comparator.comparing
        Comparator<Book> titleComparator = Comparator.comparing(
                Book::getName,
                String.CASE_INSENSITIVE_ORDER
        );

        if (!ascending) {
            titleComparator = titleComparator.reversed();
        }

        books.sort(titleComparator);
    }

    // 4. Сортировка по нескольким полям (лямбда)
    public static void sortBooksByCategoryThenPrice(List<Book> books) {
        // Составной компаратор с лямбдами
        books.sort(Comparator
                .comparing(Book::getCategory)
                .thenComparing(Book::getPrice)
        );
    }

    // 5. Фильтрация с лямбдами (Predicate)
    public static List<Book> filterBooks(List<Book> books, Predicate<Book> predicate) {
        return books.stream()
                .filter(predicate)
                .toList();
    }

    // Примеры предикатов (лямбда-выражения)

    public static Predicate<Book> cheapBooksPredicate() {
        return book -> book.getPrice().doubleValue() < 10.0;
    }

    public static Predicate<Book> recentBooksPredicate() {
        return book -> book.getYear() >= 2000;
    }

    public static Predicate<Book> categoryPredicate(String category) {
        return book -> book.getCategory().equalsIgnoreCase(category);
    }

    // Генерация компаратора через лямбду (фабричный метод)
    public static Comparator<Book> createComparator(String field, boolean ascending) {
        return switch (field.toLowerCase()) {
            case "price" -> (b1, b2) -> {
                int cmp = b1.getPrice().compareTo(b2.getPrice());
                return ascending ? cmp : -cmp;
            };
            case "year" -> (b1, b2) -> {
                int cmp = Integer.compare(b1.getYear(), b2.getYear());
                return ascending ? cmp : -cmp;
            };
            case "title" -> (b1, b2) -> {
                int cmp = b1.getName().compareToIgnoreCase(b2.getName());
                return ascending ? cmp : -cmp;
            };
            default -> (b1, b2) -> 0;
        };
    }

    // Демонстрация всех лямбда-возможностей
    public static void demonstrateLambdas(List<Book> books) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ ЛЯМБДА-ВЫРАЖЕНИЙ");
        System.out.println("=".repeat(60));

        List<Book> booksCopy = List.copyOf(books);

        // 1. Сортировка лямбдой
        System.out.println("\n1. Сортировка по цене (лямбда):");
        sortBooksByPrice(booksCopy, true);
        booksCopy.forEach(b -> System.out.println("  - " + b.getName() + ": $" + b.getPrice()));

        // 2. Фильтрация лямбдой
        System.out.println("\n2. Дешевые книги (< $10):");
        List<Book> cheapBooks = filterBooks(booksCopy, cheapBooksPredicate());
        cheapBooks.forEach(b -> System.out.println("  - " + b.getName() + ": $" + b.getPrice()));

        // 3. Кастомный компаратор через лямбду
        System.out.println("\n3. Сортировка по году (кастомный компаратор):");
        Comparator<Book> yearComparator = createComparator("year", false);
        booksCopy.sort(yearComparator);
        booksCopy.forEach(b -> System.out.println("  - " + b.getName() + " (" + b.getYear() + ")"));
    }
}
