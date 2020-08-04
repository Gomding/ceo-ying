package com.hululuuuu.ceoying.web;

import com.hululuuuu.ceoying.config.auth.LoginUser;
import com.hululuuuu.ceoying.config.auth.dto.SessionUser;
import com.hululuuuu.ceoying.domain.Pages;
import com.hululuuuu.ceoying.service.memo.MemoService;
import com.hululuuuu.ceoying.web.dto.memo.MemoResponseDto;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class MemoRestController {

    private final MemoService memoService;

    @GetMapping("/memoList")
    public ModelAndView getMemoList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        Page<MemoResponseDto> memoList = memoService.findMemoList(pageable);
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.addObject("memoList", memoList);
        mav.addObject("pages", new Pages(memoList));
        mav.setViewName("memo/memoList");
        return mav;
    }

    @PostMapping("/manage/memo")
    public Long createMemo(@RequestBody MemoSaveRequestDto requestDto) {
        return memoService.saveMemo(requestDto);
    }

    @DeleteMapping("/manage/memo/{id}")
    public Long deleteMemo(@PathVariable("id")Long id) {
        memoService.deleteMemo(id);
        return id;
    }

}
