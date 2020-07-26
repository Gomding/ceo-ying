package com.hululuuuu.ceoying.domain.wallet;

import com.hululuuuu.ceoying.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Getter
@Table
public class Wallet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private int money;

    @Column(nullable = false)
    private String record;

    @Column(nullable = false)
    private String statement;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate statementDate;

    @Builder
    public Wallet(int money, String record, String statement, LocalDate statementDate) {
        this.money = money;
        this.record = record;
        this.statement = statement;
        this.statementDate = statementDate;
    }




}
