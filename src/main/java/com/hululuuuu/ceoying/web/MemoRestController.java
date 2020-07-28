package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.service.memo.MemoService;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class MemoRestController {

    private final MemoService memoService;

    @GetMapping("/memoList")
    public ModelAndView getMemoList(@PageableDefault(size = 5) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("memo", memoService.findMemoList(pageable));
        mav.setViewName("memo/memoList");
        return mav;
    }

    @PostMapping("/memo")
    public Long createMemo(@RequestBody MemoSaveRequestDto requestDto) {
        return memoService.saveMemo(requestDto);
    }

    @DeleteMapping("/memo/{id}")
    public Long deleteMemo(@PathVariable("id")Long id) {
        memoService.deleteMemo(id);
        return id;
    }

}
