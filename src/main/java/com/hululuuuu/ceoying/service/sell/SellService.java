package com.hululuuuu.ceoying.service.sell;


import com.hululuuuu.ceoying.domain.sell.Sell;
import com.hululuuuu.ceoying.domain.sell.SellRepository;
import com.hululuuuu.ceoying.exception.NotFoundException;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import com.hululuuuu.ceoying.web.dto.sell.SellResponseDto;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SellService {

    private final SellRepository sellRepository;

    // 전체 판매 리스트 + 페이징
    @Transactional(readOnly = true)
    public Page<SellResponseDto> findSellList(Pageable pageable) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Sell> list = sellRepository.findAllDateDesc(pageable);
        return list.map(SellResponseDto::new);
    }

    // 판매 생성 메서드
    @Transactional
    public Long createSell(SellSaveRequestDto requestDto) {
        return sellRepository.save(requestDto.toEntity()).getId();
    }

    // 판매내용 최근 3가지 검색 메서드
    @Transactional(readOnly = true)
    public List<SellResponseDto> findTop3() {
        return sellRepository.findTop3ByOrderBySelldateDesc().stream()
                .map(SellResponseDto::new)
                .collect(Collectors.toList());
    }


    // 판매 수정하는 메서드
    @Transactional
    public void updateSell(SellUpdateRequestDto requestDto, Long id) {
        Sell persistSell = sellRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 판매 id 입니다."));
        persistSell.update(requestDto.getName(),
                requestDto.getProduct(),
                requestDto.getAmount(),
                requestDto.getPrice(),
                requestDto.getMethodOfPayment(),
                requestDto.getProfit(),
                requestDto.getSelldate());
    }

    // 판매 내용 삭제
    @Transactional
    public void deleteSell(Long id) {
        sellRepository.deleteById(id);
    }

    // id 로 판매의 상세정보 가져오기 - > 수정폼에 사용
    @Transactional(readOnly = true)
    public SellResponseDto sellFindById(Long id) {
        Sell entity = sellRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new SellResponseDto(entity);
    }

    // 최근 1달 판매 검색
    @Transactional(readOnly = true)
    public int sum1MonthProfit() {
        List<Sell> sell = sellRepository.findBySelldateBetween(LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1));
        return new Sell().sum1MonthProfit(sell);
    }

    // 판매 검색(날짜 기준)
    @Transactional(readOnly = true)
    public Page<SellResponseDto> searchSellList(Pageable pageable, LocalDate start, LocalDate end) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Sell> list = sellRepository.findBySelldateBetween(pageable, start, end);
        return list.map(SellResponseDto::new);
    }
}
