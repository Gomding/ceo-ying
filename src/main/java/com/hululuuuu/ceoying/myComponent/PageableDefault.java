package com.hululuuuu.ceoying.myComponent;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableDefault {

    public static Pageable setPageable(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return pageable;
    }

    public static Pageable setPageableIdDesc(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), new Sort(Sort.Direction.DESC, "id"));
        return pageable;
    }

}
