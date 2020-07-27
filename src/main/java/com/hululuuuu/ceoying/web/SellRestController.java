package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.service.sell.SellService;
import com.hululuuuu.ceoying.service.wallet.WalletService;
import com.hululuuuu.ceoying.web.dto.sell.SellResponseDto;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
public class SellRestController {

    private final SellService sellService;
    private final WalletService walletService;
    private final ProductService productService;

    @GetMapping("/sellList")
    public ModelAndView sellList(@PageableDefault Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("sellList", sellService.findAll(pageable));
        mav.setViewName("sell/sellList");
        return mav;
    }

    @GetMapping({"/sells/search", "/sells/search"})
    public ModelAndView searchSellList(@RequestParam(value = "start")String start, @RequestParam(value = "end")String end, @PageableDefault Pageable pageable) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
        ModelAndView mav = new ModelAndView();
        mav.addObject("sellList", sellService.searchSellList(pageable, startDate, endDate));
        mav.setViewName("sell/sellList");
        return mav;
    }

    @GetMapping("/sells/save")
    public ModelAndView sellForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sell/sell-save");
        return mav;
    }

    @PostMapping("/sells")
    public Long saveSell(@RequestBody SellSaveRequestDto requestDto) {
        walletService.whenSaveSell(requestDto);
        productService.updateAmountSaveSell(requestDto);

        return sellService.createSell(requestDto);
    }

    @GetMapping("/sells/{id}")
    public SellResponseDto findById(@PathVariable Long id) {
        return sellService.sellFindById(id);
    }

    @PutMapping("/sells/{id}")
    public Long updateSell(@PathVariable("id")Long id, @RequestBody SellUpdateRequestDto requestDto) {
        productService.updateAmountUpdateSell(requestDto, sellService.sellFindById(id).getAmount());
        walletService.whenDeleteSell(id);
        sellService.updateSell(requestDto, id);
        return id;
    }

    @DeleteMapping("/sells/{id}")
    public Long deleteSell(@PathVariable("id")Long id) {
        productService.updateAmountDeleteSell(sellService.sellFindById(id));
        walletService.whenDeleteSell(id);
        sellService.deleteSell(id);
        return id;
    }

}
