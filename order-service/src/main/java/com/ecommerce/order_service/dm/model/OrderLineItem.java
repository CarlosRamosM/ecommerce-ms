package com.ecommerce.order_service.dm.model;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_line_items")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private BigDecimal price;

    private Integer quantity;

    @Override
    public String toString() {
        return "OrderLineItem{" +
            "sku :'" + sku + '\'' +
            ", price :" + price +
            ", quantity : " + quantity +
            '}';
    }
}
