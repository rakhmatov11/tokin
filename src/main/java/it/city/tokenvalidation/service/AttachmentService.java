package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Attachment;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.AttachmentDto;
import it.city.tokenvalidation.repository.AttachmentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class AttachmentService {

    final AttachmentRepository attachmentRepository;


    public static final Path root = Paths.get("D:\\food-img");

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public UUID upload(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        return attachmentRepository.save(attachment).getId();
    }

    public AttachmentDto getFile(UUID id) throws MalformedURLException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetAttachment"));
        Path file = root.resolve(attachment.getName());
        Resource resource = new UrlResource(file.toUri());
        return new AttachmentDto(
                attachment.getId(),
                resource,
                attachment
        );
    }
    public UUID editAttachment(UUID id, MultipartFile file) throws IOException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Get attachment"));
//        Files.delete(root.resolve(attachment.getName()));
        Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));

        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        return attachmentRepository.save(attachment).getId();
    }

    public ApiResponse deleteAttachment(UUID id) throws IOException {
        Attachment attachment=attachmentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Delete Attachment"));
        attachmentRepository.delete(attachment);
        return new ApiResponse("Successfully deleted attachment", true);
    }
}
