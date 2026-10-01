package com.campus.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String category; // e.g., Electronics, ID Card, Bottle, Book

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private String location; // e.g., "Library 2nd Floor"

    private LocalDate dateReported;

    @Email(message = "Please provide a valid email")
    private String contactEmail;

    // Default constructor (required by JPA)
    public Item() {
        this.dateReported = LocalDate.now();
    }

    public Item(String title, String description, String category, ItemStatus status, String location, String contactEmail) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.location = location;
        this.contactEmail = contactEmail;
        this.dateReported = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public ItemStatus getStatus() { return status; }
    public void setStatus(ItemStatus status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getDateReported() { return dateReported; }
    public void setDateReported(LocalDate dateReported) { this.dateReported = dateReported; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}