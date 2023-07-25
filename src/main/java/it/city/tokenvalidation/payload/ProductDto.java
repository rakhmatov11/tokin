package it.city.tokenvalidation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private UUID attachmentId;
    private AttachmentDto attachmentDto;
    private Integer categoryId;
    private CategoryDto categoryDto;
    private double price;
    private String description;

    public ProductDto(Integer id, String name, UUID attachmentId, Integer categoryId, double price, String description) {
        this.id = id;
        this.name = name;
        this.attachmentId = attachmentId;
        this.categoryId = categoryId;
        this.price = price;
        this.description = description;
    }
}
