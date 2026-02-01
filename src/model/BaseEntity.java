package model;

import java.util.UUID;
import java.time.LocalDateTime;

public abstract class BaseEntity {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;

    public BaseEntity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public abstract void validate() throws Exception;
    public abstract String getDetails();

    public void printBasicInfo() {
        System.out.println("ID: " + id + ", Name: " + name);
    }

    public String getSummary() {
        return String.format("%s (ID: %s)",
                name,
                id.toString().substring(0, 8));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + id + ", name=" + name + "]";
    }
}