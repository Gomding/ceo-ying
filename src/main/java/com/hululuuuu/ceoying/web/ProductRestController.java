package com.hululuuuu.ceoying.web;


import com.hululuuuu.ceoying.service.product.ProductService;
import com.hululuuuu.ceoying.web.dto.product.ProductResponseDto;
import com.hululuuuu.ceoying.web.dto.product.ProductSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        mav.setViewName("product/productForm");
        return mav;
    }

    @PostMapping("/products")
    public Long saveProduct(ProductSaveRequestDto requestDto) {
        return productService.saveProduct(requestDto);
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id) {
         return productService.findById(id);
    }

    @GetMapping("/productList")
    public ModelAndView productList(@PageableDefault Pageable pageable) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("productList", productService.findAllForPaging(pageable));
        mav.setViewName("product/productList");
        return mav;
    }

    @GetMapping({"/products/search", "/products/search/"})
    public ModelAndView searchProductList(@PageableDefault Pageable pageable, @RequestParam("productName")String productName) {
        ModelAndView mav = new ModelAndView();

        if (productName.equals("") || productName.isEmpty()) {
            mav.setViewName("redirect:/product/productList");
        }
        else {
            mav.addObject("productList", productService.searchProductList(pageable, productName));
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
