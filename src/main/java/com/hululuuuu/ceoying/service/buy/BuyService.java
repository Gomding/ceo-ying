package com.hululuuuu.ceoying.service.buy;

import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
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


    @Transactional(readOnly = true)
    public Page<BuyResponseDto> findBuyList(Pageable pageable) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Buy> list = buyRepository.findAllByOrderByBuydateDesc(pageable);

        return list.map(BuyResponseDto::new);
    }


    @Transactional(readOnly = true)
    public List<Buy> findTop5() {
        return buyRepository.findTop5ByOrderByBuydateDesc();
    }


    @Transactional
    public Long saveBuy(BuySaveRequestDto requestDto) {

        return buyRepository.save(requestDto.toEntity()).getId();

    }


    @Transactional
    public void deleteBuy(Long id) {
        buyRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public BuyResponseDto findById(Long id) {
        Buy entity = buyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
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


    @Transactional(readOnly = true)
    public int lastMonthSpendMoney() {
        List<Buy> oneMonthList =
                buyRepository.findByBuydateBetween(LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1));
        return new Buy().lastMonthSpendMoney(oneMonthList);
    }


    @Transactional(readOnly = true)
    public Page<BuyResponseDto> findBuySearchList(Pageable pageable, LocalDate start, LocalDate end) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Buy> list = buyRepository.findByBuydateBetween(pageable, start, end);
        return list.map(BuyResponseDto::new);
    }

}
