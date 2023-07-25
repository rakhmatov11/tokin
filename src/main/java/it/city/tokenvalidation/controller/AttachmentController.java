package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.payload.AttachmentDto;
import it.city.tokenvalidation.service.AttachmentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class AttachmentController  {
    final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public UUID upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return attachmentService.upload(file);
    }

    @GetMapping("/getFile/{id}")
    public HttpEntity<?> getFile(@PathVariable UUID id) throws MalformedURLException {
        AttachmentDto file = attachmentService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(file.getAttachment().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getAttachment().getName())
                .body(file.getResource());
    }

    @PutMapping("/{id}")
    public UUID editFile(@PathVariable UUID id, @RequestParam(value = "file") MultipartFile file) throws IOException {
        return attachmentService.editAttachment(id, file);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteAttachment(@PathVariable UUID id ) throws IOException {
        return ResponseEntity.ok(attachmentService.deleteAttachment(id));
    }

}