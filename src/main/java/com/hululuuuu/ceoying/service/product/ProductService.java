package com.hululuuuu.ceoying.service.product;

import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.product.ProductRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import com.hululuuuu.ceoying.web.dto.product.*;
import com.hululuuuu.ceoying.web.dto.sell.SellResponseDto;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findProductList(Pageable pageable) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Product> list = productRepository.findAllModifiedDateDesc(pageable);
        return list.map(ProductResponseDto::new);
    }

    @Transactional
    public Long saveProduct(ProductSaveRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        Product entity = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new ProductResponseDto(entity);
    }

    @Transactional
    public Long updateProduct(ProductUpdateRequestDto requestDto, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        product.update(requestDto.getName(),
                        requestDto.getAmount(),
                        requestDto.getPrice(),
                        requestDto.getCostprice(),
                        requestDto.getSellByDate());

        return id;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        productRepository.delete(product);

    }

    @Transactional(readOnly = true)
    public ProductResponseDto findByProductName(String productName) {
        Product entity = productRepository.findByName(productName);
        if(entity == null) {
            return new ProductResponseDto();
        }
        return new ProductResponseDto(entity);
    }

    @Transactional
    public void updateAmountSaveSell(SellSaveRequestDto requestDto) {
        Product product = productRepository.findByName(requestDto.getProduct());
        product.updateProductAmount(requestDto.getAmount() * -1);
    }

    @Transactional
    public void updateAmountDeleteSell(SellResponseDto responseDto) {
        Product product = productRepository.findByName(responseDto.getProduct());
        product.updateProductAmount(responseDto.getAmount());
    }

    @Transactional
    public void updateAmountUpdateSell(SellUpdateRequestDto requestDto, int oldAmount) {
        Product product = productRepository.findByName(requestDto.getProduct());
        product.updateProductAmount(oldAmount - requestDto.getAmount());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> searchProductList(Pageable pageable, String productName) {
        pageable = PageableDefault.setPageable(pageable);
        Page<Product> list = productRepository.findByName(pageable, productName);
        return list.map(ProductResponseDto::new);
    }

}
