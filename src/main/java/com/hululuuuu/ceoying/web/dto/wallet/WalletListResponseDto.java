package com.hululuuuu.ceoying.web.dto.wallet;

import com.hululuuuu.ceoying.domain.wallet.Wallet;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WalletListResponseDto {

    private int money;
    private String record;
    private String statement;
    private LocalDate statementDate;

    public WalletListResponseDto(Wallet entity) {
        this.money = entity.getMoney();
        this.record = entity.getRecord();
        this.statement = entity.getStatement();
        this.statementDate = entity.getStatementDate();
    }

}
