package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

    // Основной метод для инспекции класса
    public static void inspectClass(Object obj) {
        if (obj == null) {
            System.out.println("Объект не может быть null");
            return;
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("РЕФЛЕКСИЯ: Инспекция класса " + clazz.getSimpleName());
        System.out.println("=".repeat(60));

        // 1. Информация о классе
        System.out.println("\n📦 ИНФОРМАЦИЯ О КЛАССЕ:");
        System.out.println("   Полное имя: " + clazz.getName());
        System.out.println("   Простое имя: " + clazz.getSimpleName());
        System.out.println("   Пакет: " + clazz.getPackageName());
        System.out.println("   Модификаторы: " + Modifier.toString(clazz.getModifiers()));

        // 2. Суперкласс
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            System.out.println("   Родительский класс: " + superClass.getSimpleName());
        }

        // 3. Реализуемые интерфейсы
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            System.out.println("   Реализуемые интерфейсы:");
            for (Class<?> iface : interfaces) {
                System.out.println("     - " + iface.getSimpleName());
            }
        }

        // 4. Поля (требование задания: list fields)
        System.out.println("\n🔍 ПОЛЯ:");
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            System.out.println("   Нет полей");
        } else {
            for (Field field : fields) {
                String modifiers = Modifier.toString(field.getModifiers());
                System.out.println(String.format("   %s %s %s",
                        modifiers,
                        field.getType().getSimpleName(),
                        field.getName()));

                // Показываем значения полей (если возможно)
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    System.out.println("     Значение: " + (value != null ? value.toString() : "null"));
                } catch (IllegalAccessException e) {
                    System.out.println("     Значение: недоступно");
                }
            }
        }

        // 5. Методы (требование задания: list methods)
        System.out.println("\n⚙️  МЕТОДЫ:");
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            System.out.println("   Нет методов");
        } else {
            for (Method method : methods) {
                String modifiers = Modifier.toString(method.getModifiers());
                String returnType = method.getReturnType().getSimpleName();
                String methodName = method.getName();

                // Параметры метода
                Class<?>[] params = method.getParameterTypes();
                StringBuilder paramsStr = new StringBuilder();
                for (int i = 0; i < params.length; i++) {
                    paramsStr.append(params[i].getSimpleName());
                    if (i < params.length - 1) paramsStr.append(", ");
                }

                System.out.println(String.format("   %s %s %s(%s)",
                        modifiers,
                        returnType,
                        methodName,
                        paramsStr.toString()));
            }
        }

        // 6. Аннотации (опционально)
        System.out.println("\n🏷️  АННОТАЦИИ:");
        if (clazz.getAnnotations().length == 0) {
            System.out.println("   Нет аннотаций");
        } else {
            for (java.lang.annotation.Annotation annotation : clazz.getAnnotations()) {
                System.out.println("   - " + annotation.annotationType().getSimpleName());
            }
        }

        System.out.println("=".repeat(60));
    }

    // Дополнительные утилиты

    public static void printClassHierarchy(Object obj) {
        System.out.println("\n🌳 ИЕРАРХИЯ КЛАССОВ:");
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            System.out.println("  " + clazz.getName());
            clazz = clazz.getSuperclass();
        }
    }

    public static void printAllMethods(Object obj) {
        System.out.println("\n📋 ВСЕ МЕТОДЫ (включая унаследованные):");
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                System.out.println("  " + method.getName() + "() - " + clazz.getSimpleName());
            }
            clazz = clazz.getSuperclass();
        }
    }

    // Метод для демонстрации в Main.java
    public static void demonstrateReflection() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ РЕФЛЕКСИИ (Runtime Type Inspection)");
        System.out.println("=".repeat(60));
    }
}
