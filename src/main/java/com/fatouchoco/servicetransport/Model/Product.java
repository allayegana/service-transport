package com.fatouchoco.servicetransport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Table(name = "Product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    @NotNull
    private String productName;

    @NotNull
    private String senderName;

    @NotNull
    private String senderPhone;

    @NotNull
    private String receiverName;

    @NotNull
    private String receiverPhone;

    @NotNull
    private String receiverAdress;

    @NotNull
    private int quantity;

    @NotNull
    private double price;

    @NotBlank(message = "cannot be null")
    private String description;
    private LocalDate date;

    @NotBlank(message = "cannot be null")
    private String category;

    private String status;


    public Product() {
    }

    public Product(Long id, String productId, String productName, String senderName, String senderPhone,
                   String receiverName, String receiverPhone, String receiverAdress,
                   int quantity, double price, String description, LocalDate date, String category, String status) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAdress = receiverAdress;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.date = date;
        this.category = category;
        this.status = status;
    }
}