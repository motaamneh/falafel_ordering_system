package com.motaamneh.falafel.entity;

import com.motaamneh.falafel.model.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "User cannot be null")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Restaurant cannot be null")
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Total price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total price must be 0 or greater")
    private BigDecimal totalPrice;

    @Column(name = "delivery_address", nullable = false)
    @NotNull(message = "delivery address cannot be null")
    @Size(min = 5,message = "Delivery address must be at least 5 characters")
    private String deliveryAddress;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderAddOn> orderAddOns = new ArrayList<>();

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addOrderAddOn(OrderAddOn orderAddOn){
        orderAddOns.add(orderAddOn);
        orderAddOn.setOrder(this);

    }
    public void removeOrderAddOn(OrderAddOn orderAddOn){
        orderAddOns.remove(orderAddOn);
        orderAddOn.setOrder(null);
    }




}
