package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.ProductDto;
import it.city.tokenvalidation.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.createProduct(productDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneProduct(@PathVariable Integer id){
        ProductDto product = productService.getOneProduct(id);
        return ResponseEntity.ok(product);

    }
    @GetMapping
    public HttpEntity<?> getProductList(){
        return ResponseEntity.ok( productService.getProductList());
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id,@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.editProduct(id,productDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        ApiResponse apiResponse = productService.deleteProduct(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
