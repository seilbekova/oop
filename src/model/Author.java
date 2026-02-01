package model;

import exception.InvalidInputException;  // ДОБАВИТЬ этот импорт

public class Author extends BaseEntity implements Validatable {
    private String nationality;

    public Author(String name, String nationality) {
        super(name);
        this.nationality = nationality;
    }

    @Override
    public void validate() throws InvalidInputException {  // ИЗМЕНИТЬ тип исключения
        if (getName() == null || getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }
        if (nationality == null || nationality.trim().isEmpty()) {
            throw new InvalidInputException("Nationality cannot be empty");
        }
    }

    @Override
    public String getDetails() {
        return "Author: " + getName() + " from " + nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}