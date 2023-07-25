package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.KitchenDto;
import it.city.tokenvalidation.service.KitchenService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {
    final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/list")
    public HttpEntity<?> getKitchenList() {
        return ResponseEntity.ok(kitchenService.getKitchenList());
    }

    @GetMapping("/id")
    public HttpEntity<?> getOneKitchen(@RequestParam Integer id) {
        return ResponseEntity.ok(kitchenService.getOneKitchen(id));
    }

    @PostMapping
    public HttpEntity<?> addKitchen(@RequestBody KitchenDto kitchenDto) {
        ApiResponse apiResponse = kitchenService.createKitchen(kitchenDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping({"/id"})
    public HttpEntity<?> deleteKitchen(@RequestBody Integer id) {
        ApiResponse apiResponse = kitchenService.deleteKitchen(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/id")
    public HttpEntity<?> editKitchen(@RequestBody Integer id, KitchenDto kitchenDto) {
        ApiResponse apiResponse = kitchenService.editKitchen(id, kitchenDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}
