package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.CategoryDto;
import it.city.tokenvalidation.service.CategoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/category")
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getCategoryList(){
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneCategory(@PathVariable Integer id){
        CategoryDto oneCategory = categoryService.getOneCategory(id);
        return ResponseEntity.ok(oneCategory);
    }
}