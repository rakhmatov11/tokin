package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Price;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.PriceDto;
import it.city.tokenvalidation.repository.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {
    final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public ApiResponse addPrice(PriceDto priceDto) {
        Price price = new Price();
        price.setDeliveryPrice(priceDto.getDeliveryPrice());
        price.setFromDistance(priceDto.getFromDistance());
        price.setToDistance(priceDto.getToDistance());
        priceRepository.save(price);
        return new ApiResponse("Successfully saved price", true);
    }

    public List<PriceDto> getPrice() {
        List<Price> priceList = priceRepository.findAll();
        List<PriceDto> priceDtos = new ArrayList<>();
        for (Price price : priceList) {
            PriceDto priceDto = new PriceDto();
            priceDto.setFromDistance(price.getFromDistance());
            priceDto.setDeliveryPrice(price.getDeliveryPrice());
            priceDto.setToDistance(price.getToDistance());
            priceDtos.add(priceDto);
        }
        return priceDtos;
    }

    public ApiResponse editPrice(Integer id, PriceDto priceDto) {
        Price price = priceRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getPrice"));
        price.setDeliveryPrice(priceDto.getDeliveryPrice());
        price.setToDistance(priceDto.getToDistance());
        price.setFromDistance(priceDto.getFromDistance());
        priceRepository.save(price);
        return new ApiResponse("Successfully edit Price", true);
    }

    public ApiResponse deletePrice(Integer id) {
        Price price = priceRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getPrice"));
        priceRepository.delete(price);
        return new ApiResponse("Deleted price", true);
    }

    public PriceDto getOnePrice(Integer id) {
        Price price = priceRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getPrice"));
        return new PriceDto(price.getId(), price.getFromDistance(), price.getToDistance(), price.getDeliveryPrice());
    }

}