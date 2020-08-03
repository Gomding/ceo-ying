package com.hululuuuu.ceoying.web;


import com.hululuuuu.ceoying.domain.Pages;
import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.web.dto.product.ProductResponseDto;
import com.hululuuuu.ceoying.web.dto.product.ProductSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/products/save")
    public ModelAndView productForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("product/product-save");
        return mav;
    }

    @PostMapping("/products")
    public Long saveProduct(@RequestBody ProductSaveRequestDto requestDto) {
        return productService.saveProduct(requestDto);
    }

    @GetMapping("/products/{id}")
    public ModelAndView updateProduct(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", productService.findById(id));
        mav.setViewName("product/product-update");
        return mav;
    }

    @GetMapping("/productList")
    public ModelAndView productList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        Page<ProductResponseDto> productList = productService.findAll(pageable);
        mav.addObject("productList", productList);
        mav.addObject("pages", new Pages(productList));
        mav.setViewName("product/productList");
        return mav;
    }

    @GetMapping({"/products/search", "/products/search/"})
    public ModelAndView searchProductList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam("productName")String productName) {
        ModelAndView mav = new ModelAndView();

        if (productName.equals("") || productName.isEmpty()) {
            mav.setViewName("redirect:/product/productList");
        }
        else {
            Page<ProductResponseDto> productList = productService.searchProductList(pageable, productName);
            mav.addObject("productList", productList);
            mav.addObject("pages", new Pages(productList));
            mav.setViewName("product/productList");
        }
        return mav;
    }

    @PutMapping("/products/{id}")
    public Long updateProduct(@PathVariable("id")Long id, @RequestBody ProductUpdateRequestDto requestDto) {

        return productService.updateProduct(requestDto, id);

    }

    @DeleteMapping("/products/{id}")
    public Long deleteProduct(@PathVariable("id")Long id) {
        productService.deleteProduct(id);
        return id;
    }
}
