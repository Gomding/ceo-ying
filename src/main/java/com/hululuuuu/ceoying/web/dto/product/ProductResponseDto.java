package com.hululuuuu.ceoying.web.dto.product;

import com.hululuuuu.ceoying.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductResponseDto {

    private Long id;
    private String name; // 제품명
    private int amount;  // 제품 재고
    private int price;   // 제품 가격
    private int costprice;   // 제품 원가
    private LocalDate sellByDate; // 유통기한

    public ProductResponseDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.amount = entity.getAmount();
        this.price = entity.getPrice();
        this.costprice = entity.getCostprice();
        this.sellByDate = entity.getSellByDate();
    }
}
