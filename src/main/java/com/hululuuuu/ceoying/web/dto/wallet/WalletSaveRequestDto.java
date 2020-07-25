package com.hululuuuu.ceoying.web.dto.wallet;

import com.hululuuuu.ceoying.domain.wallet.Wallet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class WalletSaveRequestDto {
    private int money;
    private String record;
    private String statement;
    private LocalDate statementDate;

    @Builder
    public WalletSaveRequestDto(int money, String record, String statement, LocalDate statementDate) {
        this.money = money;
        this.record = record;
        this.statement = statement;
        this.statementDate = statementDate;
    }

    public Wallet toEntity() {
        return Wallet.builder()
                .money(money)
                .record(record)
                .statement(statement)
                .statementDate(statementDate)
                .build();
    }

}
