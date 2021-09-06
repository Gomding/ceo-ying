package com.hululuuuu.ceoying.service.wallet;


import com.hululuuuu.ceoying.NumberUtil;
import com.hululuuuu.ceoying.domain.sell.Sell;
import com.hululuuuu.ceoying.domain.sell.SellRepository;
import com.hululuuuu.ceoying.domain.wallet.Wallet;
import com.hululuuuu.ceoying.domain.wallet.WalletRepository;
import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import com.hululuuuu.ceoying.exception.NotFoundException;
import com.hululuuuu.ceoying.web.dto.buy.BuySaveRequestDto;
import com.hululuuuu.ceoying.web.dto.buy.BuyUpdateRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import com.hululuuuu.ceoying.web.dto.wallet.WalletResponseDto;
import com.hululuuuu.ceoying.web.dto.wallet.WalletSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;

    @Transactional(readOnly = true)
    public List<WalletResponseDto> findTop5() {
        return walletRepository.findTop5().stream()
                .map(WalletResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Wallet nowWallet() {
        return walletRepository.findTop1ByOrderByIdDesc();
    }

    @Transactional
    public Long saveWallet(WalletSaveRequestDto requestDto) {
        return walletRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void whenSaveSell(SellSaveRequestDto requestDto) {
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        int sellMoney = requestDto.getProfit();
        walletRepository.save(
                Wallet.builder()
                        .money(nowWalletMoney + sellMoney)
                        .record(requestDto.getProduct())
                        .statement("+ " + sellMoney + "원")
                        .statementDate(requestDto.getSelldate())
                        .build()
        );
    }

    @Transactional
    public void whenUpdateSell(SellUpdateRequestDto requestDto, Long id) {
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        int oldSellMoney = sellRepository.getOne(id).getProfit();
        int newSellMoney = requestDto.getProfit();
        int diffMoney = newSellMoney - oldSellMoney;

        if (diffMoney != 0) {   // 돈이 변하지 않으면 계좌 내역을 생성할 필요가 없음

            walletRepository.save(
                    Wallet.builder()
                            .money(nowWalletMoney + diffMoney)
                            .record(requestDto.getProduct() + " 판매 수정")
                            .statement(NumberUtil.parseStringToSignedNumber(diffMoney) + " (" + oldSellMoney + " - > " + newSellMoney + " 수정됨)")
                            .statementDate(requestDto.getSelldate())
                            .build()
            );
        }
    }

    @Transactional
    public void whenDeleteSell(Long id) {

        Sell sell = sellRepository.getOne(id);
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        int sellMoney = sell.getProfit();
        walletRepository.save(
                Wallet.builder()
                        .money(nowWalletMoney - sellMoney)
                        .record(sell.getProduct() + "판매 취소")
                        .statement("- " + sellMoney + "원")
                        .statementDate(sell.getSelldate())
                        .build()
        );
    }

    @Transactional
    public void whenSaveBuy(BuySaveRequestDto requestDto) {
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        int buyMoney = requestDto.getPrice();
        walletRepository.save(
                Wallet.builder()
                        .money(nowWalletMoney - buyMoney)
                        .record(requestDto.getName() + " 구매")
                        .statement("- " + buyMoney + "원")
                        .statementDate(requestDto.getBuydate())
                        .build()
        );
    }

    @Transactional
    public void whenDeleteBuy(Long id) {
        Buy buy = buyRepository.getOne(id);
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        int buyMoney = buy.getPrice();
        walletRepository.save(
                Wallet.builder()
                        .money(nowWalletMoney + buyMoney)
                        .record(buy.getName() + " 구매 취소")
                        .statement("+ " + buyMoney + "원")
                        .statementDate(buy.getBuydate())
                        .build()
        );
    }

    @Transactional
    public void whenUpdateBuy(BuyUpdateRequestDto requestDto, Long id) {
        int nowWalletMoney = walletRepository.findTop1ByOrderByIdDesc().getMoney();
        Buy buy = buyRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 구매내역 입니다."));
        int oldBuyMoney = buy.getPrice();
        int newBuyMoney = requestDto.getPrice();
        int diffMoney = oldBuyMoney - newBuyMoney;

        if (diffMoney != 0) {
            walletRepository.save(
                    Wallet.builder()
                            .money(nowWalletMoney + diffMoney)
                            .record(requestDto.getName() + " 구매 수정")
                            .statement(NumberUtil.parseStringToSignedNumber(diffMoney) + " (" + oldBuyMoney + " - > " + newBuyMoney + " 수정됨)")
                            .statementDate(requestDto.getBuydate())
                            .build()
            );
        }
    }
}
