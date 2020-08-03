package com.hululuuuu.ceoying.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

    private int page;

    public Page(int page) {
        this.page = page;
    }

}
