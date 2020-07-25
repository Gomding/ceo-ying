package com.hululuuuu.ceoying.domain.sell;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {

    @Query("select s from Sell s order by s.selldate desc")
    Page<Sell> findAllDateDesc(Pageable pageable);

    List<Sell> findTop3ByOrderBySelldateDesc();

    Page<Sell> findBySelldateBetween(Pageable pageable, LocalDate start, LocalDate end);

    List<Sell> findBySelldateBetween(LocalDate start, LocalDate end);

}
