package com.hululuuuu.ceoying.domain;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pages {

    private int prevPage;
    private int nextPage;
    private List<com.hululuuuu.ceoying.domain.Page> pageNums;

    public Pages(Page<?> page) {
        int currentPage = page.getNumber();
        int startNumber = (int)(Math.floor(currentPage / 10) * 10 + 1);
        int endNumber = page.getTotalPages() > startNumber + 9 ? startNumber + 9 : page.getTotalPages();
        prevPage = currentPage;
        nextPage = currentPage + 2;
        pageNums = new ArrayList<>();
        for (int i = startNumber; i <= endNumber; i++) {
            pageNums.add(new com.hululuuuu.ceoying.domain.Page(i));
        }
    }

}
