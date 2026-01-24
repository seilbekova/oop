package model;


public class Author extends BaseEntity {
    private String nationality;

    public Author(int id, String name, String nationality) {
        super(id, name);
        this.nationality = nationality;
    }

    @Override
    public void validate() throws Exception {

        if (getName() == null || getName().trim().isEmpty()) {
            throw new Exception("Author name cannot be empty");
        }

        if (nationality == null || nationality.trim().isEmpty()) {
            throw new Exception("Nationality cannot be empty");
        }
    }

    @Override
    public String getDetails() {
        return "Author: " + getName() + " from " + nationality;
    }


    public void printFullInfo() {
        printBasicInfo();
        System.out.println("Nationality: " + nationality);
    }


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}