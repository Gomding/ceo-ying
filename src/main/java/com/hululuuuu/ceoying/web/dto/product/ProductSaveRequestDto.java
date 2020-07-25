package com.hululuuuu.ceoying.web.dto.product;

import com.hululuuuu.ceoying.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductSaveRequestDto {

    private String name; // 제품명
    private int amount;  // 제품 재고
    private int price;   // 제품 가격
    private int costprice;   // 제품 원가
    private LocalDate sellByDate; // 유통기한

    @Builder
    public ProductSaveRequestDto(String name, int amount, int price, int costprice, LocalDate sellByDate) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.costprice = costprice;
        this.sellByDate = sellByDate;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .amount(amount)
                .price(price)
                .costprice(costprice)
                .sellByDate(sellByDate)
                .build();
    }

}
