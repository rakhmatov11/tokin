package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.PriceDto;
import it.city.tokenvalidation.service.PriceService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController {
    final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public HttpEntity<?> addPrice(@RequestBody PriceDto priceDto) {
        ApiResponse apiResponse = priceService.addPrice(priceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getPriceList() {
        List<PriceDto> price = priceService.getPrice();
        return ResponseEntity.ok(price);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editPrice(@PathVariable Integer id, @RequestBody PriceDto priceDto) {
        ApiResponse apiResponse = priceService.editPrice(id, priceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePrice(@PathVariable Integer id) {
        ApiResponse apiResponse = priceService.deletePrice(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOnePrice(@PathVariable Integer id) {
        PriceDto onePrice = priceService.getOnePrice(id);
        return ResponseEntity.ok(onePrice);
    }
}