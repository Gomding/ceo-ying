package com.hululuuuu.ceoying.domain.yiying;

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
public class Buy extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      // 구매 고유 아이디

    @Column
    private String name;  // 구매한 상품의 이름

    @Column(nullable = false)
    private int price;   // 구매한 상품의 가격

    @Column(nullable = false)
    private int amount;   // 구매한 물품 수량

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate buydate;    // 구매한 날짜

    @Builder
    public Buy(String name, int price, int amount, String content, LocalDate buydate) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.content = content;
        this.buydate = buydate;
    }

    public void update(String name, int price, int amount, String content, LocalDate buydate) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.content = content;
        this.buydate = buydate;
    }

    public int lastMonthSpendMoney(List<Buy> oneMonthList) {
        int sum = 0;
        for (Buy buy : oneMonthList) {
            sum += buy.price;
        }

        return sum;
    }

}
