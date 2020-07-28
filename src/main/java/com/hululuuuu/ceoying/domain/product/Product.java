package com.hululuuuu.ceoying.domain.product;

import com.hululuuuu.ceoying.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Product extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 제품명

    @Column(nullable = false)
    private int amount;  // 제품 재고

    @Column
    private int price;   // 제품 가격

    @Column
    private int costprice;   // 제품 원가

    @Column(nullable = false)
    private LocalDate sellByDate;

    @Builder
    public Product(String name, int amount, int price, int costprice, LocalDate sellByDate) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.costprice = costprice;
        this.sellByDate = sellByDate;
    }

    public void update(String name, int amount, int price, int costprice, LocalDate sellByDate) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.costprice = costprice;
        this.sellByDate = sellByDate;
    }

    public void updateProductAmount(int amount) {
        this.amount += amount;
    }

}
