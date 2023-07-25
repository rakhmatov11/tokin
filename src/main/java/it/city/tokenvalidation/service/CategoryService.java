package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Attachment;
import it.city.tokenvalidation.entity.Category;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.CategoryDto;
import it.city.tokenvalidation.repository.AttachmentRepository;
import it.city.tokenvalidation.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;
    final AttachmentRepository attachmentRepository;

    public CategoryService(CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public CategoryDto getOneCategory(Integer id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetCategory"));
        return new CategoryDto(category.getId(), category.getName(), category.getAttachment().getId(), category.isDisplay());
    }


    public ApiResponse createCategory(CategoryDto categoryDto){
        Attachment attachment = attachmentRepository.findById(categoryDto.getAttachmentId()).orElseThrow(() -> new ResourceAccessException("getAttachment"));
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setAttachment(attachment);
        categoryRepository.save(category);
        return new ApiResponse("Successfully saved category", true);
    }

    public List<CategoryDto> getCategoryList(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos=new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto=new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setDisplay(category.isDisplay());
            categoryDto.setAttachmentId(category.getAttachment().getId());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    public ApiResponse editCategory(Integer id, CategoryDto categoryDto){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getCategory"));
        Attachment attachment = attachmentRepository.findById(categoryDto.getAttachmentId()).orElseThrow(() -> new ResourceAccessException("getAttachment"));
        category.setDisplay(categoryDto.isDisplay());
        category.setName(categoryDto.getName());
        category.setAttachment(attachment);
        categoryRepository.save(category);
        return new ApiResponse("Edit category", true);
    }



    public ApiResponse deleteCategory(Integer id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getCategory"));
        categoryRepository.delete(category);
        return new ApiResponse("Delete Category", true);
    }


}