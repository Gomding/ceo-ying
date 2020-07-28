package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.service.buy.BuyService;
import com.hululuuuu.ceoying.service.wallet.WalletService;
import com.hululuuuu.ceoying.web.dto.buy.BuyResponseDto;
import com.hululuuuu.ceoying.web.dto.buy.BuySaveRequestDto;
import com.hululuuuu.ceoying.web.dto.buy.BuyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
public class BuyRestController {

    private final BuyService buyService;
    private final WalletService walletService;

    @GetMapping("/buyList")
    public ModelAndView buyList(@PageableDefault Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("buyList", buyService.findBuyList(pageable));
        mav.setViewName("yiying/buyList");
        return mav;
    }

    @GetMapping("/buy/save")
    public ModelAndView buyForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("yiying/buy-save");
        return mav;
    }

    @PostMapping("/buy")
    public Long saveBuy(@RequestBody BuySaveRequestDto requestDto) {

        walletService.whenSaveBuy(requestDto);

        return buyService.saveBuy(requestDto);
    }

    @GetMapping("/buy/read/{id}")
    public ModelAndView buyDetail(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("buy", buyService.findById(id));
        mav.setViewName("yiying/buy-detail");
        return mav;
    }

    @GetMapping("/buy/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("buy", buyService.findById(id));
        mav.setViewName("yiying/buy-update");
        return mav;
    }

    @GetMapping({"/buy/search", "/buy/search/"})
    public ModelAndView searchSellList(@RequestParam(value = "start")String start, @RequestParam(value = "end")String end, @PageableDefault Pageable pageable) {
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
        ModelAndView mav = new ModelAndView();
        mav.addObject("buyList", buyService.buySearchList(pageable, startDate, endDate));
        mav.setViewName("yiying/buyList");
        return mav;
    }

    @DeleteMapping("/buy/{id}")
    public Long deleteBuy(@PathVariable("id")Long id) {
        walletService.whenDeleteBuy(id);
        buyService.deleteBuy(id);
        return id;
    }

    @PutMapping("/buy/{id}")
    public Long updateBuy(@PathVariable("id") Long id, @RequestBody BuyUpdateRequestDto requestDto) {
        walletService.whenUpdateBuy(requestDto, id);
        return buyService.updateBuy(requestDto, id);
    }

}
