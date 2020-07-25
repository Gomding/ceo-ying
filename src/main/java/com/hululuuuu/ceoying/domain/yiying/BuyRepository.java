package com.hululuuuu.ceoying.domain.yiying;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {

    List<Buy> findTop5ByOrderByBuydateDesc();

    @Query(nativeQuery=true, value="select b from Buy b where b.buydate order by desc")
    Page<Buy> findAllDesc(Pageable pageable);


    List<Buy> findByBuydateBetween(LocalDate start, LocalDate end);

    Page<Buy> findByBuydateBetween(Pageable pageable, LocalDate start, LocalDate end);
}
