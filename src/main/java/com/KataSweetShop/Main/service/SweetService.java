package com.KataSweetShop.Main.service;

import com.KataSweetShop.Main.dto.SweetRequest;
import com.KataSweetShop.Main.dto.SweetResponse;
import com.KataSweetShop.Main.entity.Sweet;
import com.KataSweetShop.Main.exception.BadRequestException;
import com.KataSweetShop.Main.exception.ResourceNotFoundException;
import com.KataSweetShop.Main.repository.SweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    //ADD

    public SweetResponse addSweet(SweetRequest req) {
        Sweet sweet = Sweet.builder()
                .name(req.getName())
                .category(req.getCategory())
                .price(req.getPrice())
                .quantity(req.getQuantity())
                .build();

        return mapToResponse(sweetRepository.save(sweet));
    }

    //GET ALL

    public List<SweetResponse> listall() {
        return sweetRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //UPDATE

    public SweetResponse updateSweet(Long id, SweetRequest req) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sweet not found with id: " + id));

        sweet.setName(req.getName());
        sweet.setCategory(req.getCategory());
        sweet.setPrice(req.getPrice());
        sweet.setQuantity(req.getQuantity());

        return mapToResponse(sweetRepository.save(sweet));
    }

    //DELETE

    public void deleteSweet(Long id) {
        if (!sweetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sweet not found with id: " + id);
        }
        sweetRepository.deleteById(id);
    }

    //SEARCH

    public List<SweetResponse> search(String name, String category, Long minPrice, Long maxPrice) {

        if (name != null && name.isBlank()) name = null;
        if (category != null && category.isBlank()) category = null;

        return sweetRepository.searchSweets(name, category, minPrice, maxPrice)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    //PURCHASE

    public SweetResponse purchaseSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sweet not found with id: " + id));

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        if (sweet.getQuantity() < quantity) {
            throw new BadRequestException("Insufficient quantity available");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        return mapToResponse(sweetRepository.save(sweet));
    }
    //RESTOCK

    public SweetResponse restockSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sweet not found with id: " + id));

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        sweet.setQuantity(sweet.getQuantity() + quantity);
        return mapToResponse(sweetRepository.save(sweet));
    }

    //MAPPER

    private SweetResponse mapToResponse(Sweet sweet) {
        return SweetResponse.builder()
                .id(sweet.getId())
                .name(sweet.getName())
                .category(sweet.getCategory())
                .price(sweet.getPrice())
                .quantity(sweet.getQuantity())
                .build();
    }
}
