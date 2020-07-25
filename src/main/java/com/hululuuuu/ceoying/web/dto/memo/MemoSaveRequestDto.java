package com.hululuuuu.ceoying.web.dto.memo;


import com.hululuuuu.ceoying.domain.memo.Memo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemoSaveRequestDto {

    private String content;
    private String link;
    private String name;

    @Builder
    public MemoSaveRequestDto(String content, String link, String name) {
        this.content = content;
        this.link = link;
        this.name = name;
    }

    public Memo toEntity() {
        return Memo.builder()
                .content(content)
                .link(link)
                .name(name)
                .build();
    }

}
