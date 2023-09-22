package com.saadahmedev.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    private long userId;
    private double totalPrice;
    private int discount;
    private double grandTotal;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> products;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ShippingDetails shippingDetails;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PaymentStatus paymentStatus;
    private Date createdAt;
    private Date updatedAt;
}
