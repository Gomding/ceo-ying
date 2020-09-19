package com.hululuuuu.ceoying.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    @Query("select p from Product p order by p.modifiedDate desc ")
    Page<Product> findAllModifiedDateDesc(Pageable pageable);

    Page<Product> findByName(Pageable pageable, String name);

}
