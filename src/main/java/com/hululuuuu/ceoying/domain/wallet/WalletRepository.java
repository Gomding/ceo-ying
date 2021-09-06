package com.hululuuuu.ceoying.domain.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findTop1ByOrderByIdDesc();

    @Query(nativeQuery = true, value = "select * from Wallet order by id desc limit 5")
    List<Wallet> findTop5();
}
