package it.city.tokenvalidation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;
    private String name;
    private UUID attachmentId;
    private AttachmentDto attachmentDto;
    private boolean isDisplay;

    public CategoryDto(Integer id, String name, UUID attachmentId, boolean isDisplay) {
        this.id = id;
        this.name = name;
        this.attachmentId = attachmentId;
        this.isDisplay = isDisplay;
    }
}
