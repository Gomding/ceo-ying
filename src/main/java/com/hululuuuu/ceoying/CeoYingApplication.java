package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CeoYingApplication {

    @Autowired
    private WalletRepository walletRepository;

    public static void main(String[] args) {



        SpringApplication.run(CeoYingApplication.class, args);
    }

}
