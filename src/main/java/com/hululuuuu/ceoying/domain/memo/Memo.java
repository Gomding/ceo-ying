package com.hululuuuu.ceoying.domain.memo;

import com.hululuuuu.ceoying.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table
public class Memo extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String link;

    @Column
    private String name;

    @Builder
    public Memo(String content, String link, String name) {
        this.content = content;
        this.link = link;
        this.name = name;
    }

    public void update(String content, String link) {
        this.content = content;
        this.link = link;
    }

}
