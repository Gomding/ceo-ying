package com.hululuuuu.ceoying.domain.memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Query("select m from Memo m order by m.createDate desc ")
    List<Memo> findAllDesc();

}
