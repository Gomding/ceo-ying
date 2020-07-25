package com.hululuuuu.ceoying.domain.sell;

import com.hululuuuu.ceoying.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table
public class Sell extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 판매 아이디

    @Column
    private String name;        // 구매자

    @Column
    private String product;      // 판매 상품

    @Column
    private int amount;         // 판매 수량

    @Column
    private int price;          // 판매 금액

    @Column
    private String methodOfPayment;         // 결제수단

    @Column
    private int profit;      // 수익

    @Column
    private LocalDate selldate;

    @Builder
    public Sell(String name, String product, int amount, int price, String methodOfPayment, int profit, LocalDate selldate) {
        this.name = name;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.methodOfPayment = methodOfPayment;
        this.profit = profit;
        this.selldate = selldate;
    }

    public void update(String name, String product, int amount, int price, String methodOfPayment, int profit, LocalDate selldate) {
        this.name = name;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.methodOfPayment = methodOfPayment;
        this.profit = profit;
        this.selldate = selldate;
    }

    // 최근 한달 수익의 합
    public int sum1MonthProfit(List<Sell> list) {
        int sum = 0;
        for (Sell sell : list) {
            sum += sell.getProfit();
        }
        return sum;
    }

}
