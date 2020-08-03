package com.hululuuuu.ceoying.web.dto.memo;

import com.hululuuuu.ceoying.domain.memo.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MemoResponseDto {
    private Long id;
    private String content;
    private String link;
    private String name;
    private LocalDateTime createDate;


    public MemoResponseDto(Memo entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.link = entity.getLink();
        this.name = entity.getName();
        this.createDate = entity.getCreateDate();
    }
}
