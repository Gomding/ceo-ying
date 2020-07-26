package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.sell.Sell;
import com.hululuuuu.ceoying.domain.wallet.Wallet;
import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.service.buy.BuyService;
import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.service.sell.SellService;
import com.hululuuuu.ceoying.service.wallet.WalletService;
import com.hululuuuu.ceoying.web.dto.product.ProductResponseDto;
import com.hululuuuu.ceoying.web.dto.sell.SellListResponseDto;
import com.hululuuuu.ceoying.web.dto.wallet.WalletListResponseDto;
import com.hululuuuu.ceoying.web.dto.wallet.WalletSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final BuyService buyService;
    private final SellService sellService;
    private final ProductService productService;
    private final WalletService walletService;

    @GetMapping({"/", "/main"})
    public ModelAndView index() {

        WalletSaveRequestDto requestDto = WalletSaveRequestDto.builder()
                .money(0)
                .record("0")
                .statement("개설")
                .statementDate(LocalDate.now())
                .build();

        walletService.saveWallet(requestDto);
        ModelAndView mav = new ModelAndView();
        List<Buy> buyTop5 = buyService.findTop5();
        List<SellListResponseDto> sellTop3 = sellService.findTop3();
        //ProductResponseDto toothPaste = productService.findByName("치약");
        //List<WalletListResponseDto> wallet = walletService.findTop5();
        //int walletMoney = walletService.nowWallet();
        int oneMonthProfit = sellService.sum1MonthProfit();
        int oneMonthSpendMoney = buyService.sum1MonthSpendMoney();

        mav.addObject("oneMonthProfit", oneMonthProfit);
        mav.addObject("oneMonthSpendMoney", oneMonthSpendMoney);
        mav.addObject("buyTop", buyTop5);
        mav.addObject("sellTop", sellTop3);
        //mav.addObject("toothPaste", toothPaste);
        //mav.addObject("nowWallet", walletMoney);
        //mav.addObject("walletTop5", wallet);
        mav.setViewName("index");
        return mav;
    }



}
