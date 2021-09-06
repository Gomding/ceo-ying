package com.hululuuuu.ceoying.web;


import com.hululuuuu.ceoying.config.auth.LoginUser;
import com.hululuuuu.ceoying.config.auth.dto.SessionUser;
import com.hululuuuu.ceoying.domain.Pages;
import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.web.dto.product.ProductResponseDto;
import com.hululuuuu.ceoying.web.dto.product.ProductSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/productList")
    public ModelAndView productList(@PageableDefault Pageable pageable, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        Page<ProductResponseDto> productList = productService.findProductList(pageable);
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.addObject("productList", productList);
        mav.addObject("pages", new Pages(productList));
        mav.setViewName("product/productList");
        return mav;
    }

    @GetMapping({"/products/search", "/products/search/"})
    public ModelAndView searchProductList(@RequestParam("productName") String productName, @PageableDefault Pageable pageable, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        if (productName.equals("") || productName.isEmpty()) {
            mav.setViewName("redirect:/productList");
        } else {
            Page<ProductResponseDto> productList = productService.searchProductList(pageable, productName);
            mav.addObject("productList", productList);
            mav.addObject("pages", new Pages(productList));
            mav.setViewName("product/productList");
        }
        return mav;
    }

    @GetMapping("/manage/products/save")
    public ModelAndView productForm(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.setViewName("product/product-save");
        return mav;
    }

    @PostMapping("/manage/products")
    public Long saveProduct(@RequestBody ProductSaveRequestDto requestDto) {
        return productService.saveProduct(requestDto);
    }

    @GetMapping("/manage/products/{id}")
    public ModelAndView updateProductForm(@PathVariable Long id, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject("userName", user.getName());
        }
        mav.addObject("product", productService.findById(id));
        mav.setViewName("product/product-update");
        return mav;
    }

    @PutMapping("/manage/products/{id}")
    public Long updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateRequestDto requestDto) {

        return productService.updateProduct(requestDto, id);

    }

    @DeleteMapping("/manage/products/{id}")
    public Long deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return id;
    }
}
