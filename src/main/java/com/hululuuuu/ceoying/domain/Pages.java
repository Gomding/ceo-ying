package com.hululuuuu.ceoying.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class Pages {

    private int firstPage;
    private int lastPage;
    private int currentPage;

    @Builder
    public Pages(int firstPage, int lastPage, int currentPage) {
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.currentPage = currentPage;
    }

    public void toPage(Page<?> page) {
        page.getTotalPages();
    }

}
