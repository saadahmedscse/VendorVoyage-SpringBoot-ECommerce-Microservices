package com.saadahmedev.orderservice.entity;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private double totalPrice;
    private int discount;
    private double grandTotal;
    @ElementCollection
    private List<Long> productIds;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @OneToOne(fetch = FetchType.EAGER)
    private ShippingDetails shippingDetails;
    @OneToOne(fetch = FetchType.EAGER)
    private PaymentStatus paymentStatus;
    private Date createdAt;
    private Date updatedAt;
}
