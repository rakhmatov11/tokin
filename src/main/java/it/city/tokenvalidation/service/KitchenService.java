package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Category;
import it.city.tokenvalidation.entity.Kitchen;
import it.city.tokenvalidation.entity.Product;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.CategoryDto;
import it.city.tokenvalidation.payload.KitchenDto;
import it.city.tokenvalidation.payload.ProductDto;
import it.city.tokenvalidation.repository.AttachmentRepository;
import it.city.tokenvalidation.repository.CategoryRepository;
import it.city.tokenvalidation.repository.KitchenRepository;
import it.city.tokenvalidation.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class KitchenService {

    final KitchenRepository kitchenRepository;
    final AttachmentRepository attachmentRepository;
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;

    public KitchenService(KitchenRepository kitchenRepository, AttachmentRepository attachmentRepository,
                          ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.kitchenRepository = kitchenRepository;
        this.attachmentRepository = attachmentRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    public List<KitchenDto> getKitchenList() {
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        List<KitchenDto> kitchens = new ArrayList<>();
        for (Kitchen kitchen : kitchenList) {
            KitchenDto kitchenDto=new KitchenDto(
                    kitchen.getId(),
                    kitchen.getName(),
                    kitchen.getAttachment().getId(),
                    kitchen.getDeliveryTime(),
                    kitchen.getDeliveryPrice(),
                    kitchen.isDiscount(),
                    kitchen.getDiscountPrice(),
                    kitchen.getInfo(),
                    kitchen.getStartTime(),
                    kitchen.getFinishTime(),
                    kitchen.isOpen(),
                    kitchen.getLan(),
                    kitchen.getLat()
            );
            List<Integer> categoriesId = new ArrayList<>();
            for (Category category : kitchen.getCategories()) {
                Integer categoryId = category.getId();
                categoriesId.add(categoryId);
            }
            List<Integer> productsId = new ArrayList<>();
            for (Product product : kitchen.getProducts()) {
                Integer productId = product.getId();
                productsId.add(productId);
            }
            kitchenDto.setCategoryId(categoriesId);
            kitchenDto.setProductsId(productsId);
            kitchens.add(kitchenDto);
        }
        return kitchens;
    }
    public ApiResponse createKitchen(KitchenDto kitchenDto) {
        boolean exist = kitchenRepository.existsByName(kitchenDto.getName());
        if (!exist) {
            setKitchenNames(new Kitchen(), kitchenDto);
            return new ApiResponse("Successfully saved kitchen", true);
        }
        return new ApiResponse("Name already exist", false);
    }
    public ApiResponse deleteKitchen(Integer id) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getKitchen"));
        kitchenRepository.delete(kitchen);
        return new ApiResponse("Successfully deleted kitchen!", true);
    }
    public ApiResponse editKitchen(Integer id, KitchenDto kitchenDto) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getKitchen"));
        boolean exist = kitchenRepository.existsByName(kitchenDto.getName());
        if (!exist) {
            setKitchenNames(kitchen, kitchenDto);
            return new ApiResponse("Successfully saved kitchen", true);
        }
        return new ApiResponse("Name already exist", false);
    }
    public KitchenDto getOneKitchen(Integer id) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getKitchen"));
        KitchenDto kitchenDto = new KitchenDto();
        kitchenDto.setName(kitchen.getName());
        kitchenDto.setAttachmentId(kitchen.getAttachment().getId());
        kitchenDto.setCategoryDto(getKitchenCategories(kitchen));
        kitchenDto.setProductDto(getKitchenProducts(kitchen));
        kitchenDto.setInfo(kitchen.getInfo());
        kitchenDto.setDiscount(kitchen.isDiscount());
        kitchenDto.setLan(kitchenDto.getLan());
        kitchenDto.setLat(kitchenDto.getLat());
        kitchenDto.setDeliveryPrice(kitchen.getDeliveryPrice());
        kitchenDto.setDiscountPrice(kitchen.getDiscountPrice());
        kitchenDto.setDeliveryTime(kitchen.getDeliveryTime());
        return kitchenDto;
    }
    public List<CategoryDto> getKitchenCategories(Kitchen kitchen){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : kitchen.getCategories()) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setAttachmentId(category.getAttachment().getId());
            categoryDto.setDisplay(category.isDisplay());
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }
    public List<ProductDto> getKitchenProducts(Kitchen kitchen){
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : kitchen.getProducts()) {
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setAttachmentId(product.getAttachment().getId());
            productDto.setPrice(product.getPrice());
            Category getCategory = categoryRepository.findById(product.getCategory().getId()).orElseThrow(() ->
                    new ResourceAccessException("getCategory"));
            CategoryDto categoryDto = new CategoryDto(getCategory.getId(), getCategory.getName(),
                    getCategory.getAttachment().getId(), getCategory.isDisplay());
            productDto.setCategoryDto(categoryDto);
            productDto.setDescription(product.getDescription());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
    public List<Category> setKitchenCategory(KitchenDto kitchenDto) {
        List<Category> categories = new ArrayList<>();
        for (Integer id : kitchenDto.getCategoryId()) {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getCategory"));
            categories.add(category);
        }
        return categories;
    }
    public List<Product> setKitchenProduct(KitchenDto kitchenDto){
        List<Product> products = new ArrayList<>();
        for (Integer id : kitchenDto.getProductsId()) {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getProduct"));
            products.add(product);
        }
        return products;
    }
    public void setKitchenNames(Kitchen kitchen, KitchenDto kitchenDto) {
        kitchen.setAttachment(attachmentRepository.findById(kitchenDto.getAttachmentId()).orElseThrow(() ->
                new ResourceAccessException("getAttachment")));
        kitchen.setId(kitchenDto.getId());
        kitchen.setName(kitchenDto.getName());
        kitchen.setProducts(setKitchenProduct(kitchenDto));
        kitchen.setCategories(setKitchenCategory(kitchenDto));
        kitchen.setDeliveryPrice(kitchenDto.getDeliveryPrice());
        kitchen.setDiscount(kitchenDto.isDiscount());
        kitchen.setDeliveryTime(kitchenDto.getDeliveryTime());
        kitchen.setFinishTime(kitchenDto.getFinishTime());
        kitchen.setStartTime(kitchenDto.getStartTime());
        kitchen.setInfo(kitchenDto.getInfo());
        kitchen.setLan(kitchenDto.getLan());
        kitchen.setLat(kitchenDto.getLat());
        kitchenRepository.save(kitchen);
    }

}
