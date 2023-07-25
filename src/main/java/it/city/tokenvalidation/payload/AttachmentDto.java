package it.city.tokenvalidation.payload;

import it.city.tokenvalidation.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private UUID id;
    private Resource resource;
    private Attachment attachment;
}
