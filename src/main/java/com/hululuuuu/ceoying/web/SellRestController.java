package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.config.auth.LoginUser;
import com.hululuuuu.ceoying.config.auth.dto.SessionUser;
import com.hululuuuu.ceoying.domain.Pages;
import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.service.sell.SellService;
import com.hululuuuu.ceoying.service.wallet.WalletService;
import com.hululuuuu.ceoying.web.dto.sell.SellResponseDto;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
public class SellRestController {

    private final SellService sellService;
    private final WalletService walletService;
    private final ProductService productService;

    @GetMapping("/sellList")
    public ModelAndView sellList(@PageableDefault Pageable pageable, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        Page<SellResponseDto> sellList = sellService.findSellList(pageable);
        mav.addObject("sellList", sellList);
        mav.addObject("pages", new Pages(sellList));
        mav.setViewName("sell/sellList");
        return mav;
    }

    @GetMapping({"/sells/search", "/sells/search"})
    public ModelAndView searchSellList(@RequestParam(value = "start")String start,
                                       @RequestParam(value = "end")String end,
                                       @PageableDefault Pageable pageable,
                                       @LoginUser SessionUser user) {
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        Page<SellResponseDto> sellList = sellService.searchSellList(pageable, startDate, endDate);
        mav.addObject("sellList", sellList);
        mav.addObject("pages", new Pages(sellList));
        mav.setViewName("sell/sellList");
        return mav;
    }

    @GetMapping("/manage/sells/save")
    public ModelAndView sellForm(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.setViewName("sell/sell-save");
        return mav;
    }

    @PostMapping("/manage/sells")
    public Long saveSell(@RequestBody SellSaveRequestDto requestDto) {
        walletService.whenSaveSell(requestDto);
        productService.updateAmountSaveSell(requestDto);
        return sellService.createSell(requestDto);
    }

    @GetMapping("/manage/sells/{id}")
    public ModelAndView updateSellForm(@PathVariable Long id, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.addObject("sell", sellService.sellFindById(id));
        mav.setViewName("sell/sell-update");
        return mav;
    }

    @PutMapping("/manage/sells/{id}")
    public Long updateSell(@PathVariable("id")Long id, @RequestBody SellUpdateRequestDto requestDto) {
        productService.updateAmountUpdateSell(requestDto, sellService.sellFindById(id).getAmount());
        walletService.whenUpdateSell(requestDto, id);
        sellService.updateSell(requestDto, id);
        return id;
    }

    @DeleteMapping("/manage/sells/{id}")
    public Long deleteSell(@PathVariable("id")Long id) {
        productService.updateAmountDeleteSell(sellService.sellFindById(id));
        walletService.whenDeleteSell(id);
        sellService.deleteSell(id);
        return id;
    }

}
