package com.hululuuuu.ceoying.web.dto.wallet;

import com.hululuuuu.ceoying.domain.wallet.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class WalletResponseDto {

    private Long id;
    private int money;
    private String record;
    private String statement;
    private LocalDate statementDate;

    public WalletResponseDto(Wallet entity) {
        this.id = entity.getId();
        this.money = entity.getMoney();
        this.record = entity.getRecord();
        this.statement = entity.getStatement();
        this.statementDate = entity.getStatementDate();
    }

}
