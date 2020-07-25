package com.hululuuuu.ceoying.service.buy;

import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import com.hululuuuu.ceoying.myComponent.TypeTranslator;
import com.hululuuuu.ceoying.web.dto.buy.BuyListResponseDto;
import com.hululuuuu.ceoying.web.dto.buy.BuyResponseDto;
import com.hululuuuu.ceoying.web.dto.buy.BuySaveRequestDto;
import com.hululuuuu.ceoying.web.dto.buy.BuyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BuyService {

    private final BuyRepository buyRepository;

    public Page<BuyListResponseDto> findBuyList(Pageable pageable) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        pageable = PageableDefault.setPageable(pageable);
        Page<Buy> list = buyRepository.findAllDesc(pageable);

        return TypeTranslator.domainPageToDTOPage(list, "BuyListResponseDto");
    }

    public List<Buy> findTop5() {
        return buyRepository.findTop5ByOrderByBuydateDesc();
    }

    @Transactional
    public Long createBuyList(BuySaveRequestDto requestDto) {

        return buyRepository.save(requestDto.toEntity()).getId();

    }

    @Transactional
    public void deleteBuy(Long id) {
        buyRepository.deleteById(id);
    }

    public BuyResponseDto findById(Long id) {
        Buy entity = buyRepository.getOne(id);
        return new BuyResponseDto(entity);
    }

    @Transactional
    public Long updateBuy(BuyUpdateRequestDto requestDto, Long id) {
        Buy buy = buyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        buy.update(requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getAmount(),
                requestDto.getContent(),
                requestDto.getBuydate());
        return id;
    }

    public int sum1MonthSpendMoney() {
        List<Buy> oneMonthList =
                buyRepository.findByBuydateBetween(LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1));
        return new Buy().sum1MonthSpendMoney(oneMonthList);
    }

    public Page<BuyListResponseDto> buySearchList(Pageable pageable, LocalDate start, LocalDate end) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        pageable = PageableDefault.setPageable(pageable);
        Page<Buy> list = buyRepository.findByBuydateBetween(pageable, start, end);

        return TypeTranslator.domainPageToDTOPage(list, "BuyListResponseDto");
    }

}
