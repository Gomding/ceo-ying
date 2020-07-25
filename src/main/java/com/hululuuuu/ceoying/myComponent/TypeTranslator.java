package com.hululuuuu.ceoying.myComponent;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class TypeTranslator {

    public static <F, T> Page<T> domainPageToDTOPage(Page<F> domainPage, String dtoClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<T> dtoList = new ArrayList<>();
        Class dtoClass = Class.forName(dtoClassName);

        for (F e : domainPage) {
            Object dto = dtoClass.newInstance();
            BeanUtils.copyProperties(e, dtoClass.cast(dto));
            dtoList.add((T) dto);
        }
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }
}
