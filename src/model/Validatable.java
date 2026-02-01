package model;

import exception.InvalidInputException;  // ДОБАВИТЬ этот импорт

public interface Validatable {
    void validate() throws InvalidInputException;  // ИЗМЕНИТЬ: throws InvalidInputException вместо Exception

    default boolean quickValidate() {
        try {
            validate();
            return true;
        } catch (InvalidInputException e) {  // ИЗМЕНИТЬ тип исключения
            return false;
        }
    }
}