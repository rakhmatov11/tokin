package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Attachment;
import it.city.tokenvalidation.entity.Category;
import it.city.tokenvalidation.entity.Product;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.ProductDto;
import it.city.tokenvalidation.repository.AttachmentRepository;
import it.city.tokenvalidation.repository.CategoryRepository;
import it.city.tokenvalidation.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    final ProductRepository productRepository;
    final AttachmentRepository attachmentRepository;
    final CategoryRepository categoryRepository;
    final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, AttachmentRepository attachmentRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    public ApiResponse createProduct(ProductDto productDto) {
        Attachment attachment = attachmentRepository.findById(productDto.getAttachmentId()).orElseThrow(() -> new ResourceAccessException("getAttachment"));
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceAccessException("getCategory"));
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAttachment(attachment);
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        productRepository.save(product);
        return new ApiResponse("Successfully saved product", true);
    }

    public List<ProductDto> getProductList() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = new ProductDto();
            productDto.setAttachmentId(product.getAttachment().getId());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setName(product.getName());
            productDto.setCategoryDto(categoryService.getOneCategory(product.getCategory().getId()));
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    public ApiResponse deleteProduct(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getProduct"));
        productRepository.delete(product);
        return new ApiResponse("Delete Product",true);
    }

    public ApiResponse editProduct(Integer id,ProductDto productDto){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getProduct"));
        Attachment attachment = attachmentRepository.findById(productDto.getAttachmentId()).orElseThrow(() -> new ResourceAccessException("getAttachment"));
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceAccessException("getCategory"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAttachment(attachment);
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        productRepository.save(product);
        return new ApiResponse("Successfully saved product", true);
    }

    public ProductDto getOneProduct(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getProduct"));
        ProductDto productDto = new ProductDto();
        productDto.setAttachmentId(product.getAttachment().getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setCategoryDto(categoryService.getOneCategory(product.getCategory().getId()));
        return productDto;
    }

}
