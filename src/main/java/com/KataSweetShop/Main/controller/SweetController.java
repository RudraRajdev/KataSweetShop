package com.KataSweetShop.Main.controller;

import com.KataSweetShop.Main.dto.SweetRequest;
import com.KataSweetShop.Main.dto.SweetResponse;
import com.KataSweetShop.Main.service.SweetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {
   private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;

    }
    @PostMapping()
    public ResponseEntity<SweetResponse>addSweet(@RequestBody @Valid  SweetRequest req){
        return ResponseEntity.ok(sweetService.addSweet(req));

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id){
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();

    }
    @PutMapping("{id}")
    public ResponseEntity<SweetResponse> updateSweet(
            @PathVariable Long id,
           @Valid @RequestBody SweetRequest req
    ){
        return ResponseEntity.ok(sweetService.updateSweet(id, req));
    }
    @GetMapping()
    public ResponseEntity<List<SweetResponse>>listAll(){
        return ResponseEntity.ok( sweetService.listall());
    }
    @GetMapping("/search")
    public ResponseEntity<List<SweetResponse>> searchSweets(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)String category,
            @RequestParam(required = false)Long minPrice,
            @RequestParam(required = false)Long maxPrice
    ){
        return ResponseEntity.ok(sweetService.search(name, category, minPrice, maxPrice));
    }
    @PostMapping("/{id}/purchase")
    public ResponseEntity<SweetResponse>purchaseSweet(@PathVariable Long id,@RequestParam int quantity){
        return ResponseEntity.ok(sweetService.purchaseSweet(id,quantity));
    }
    @PostMapping("/{id}/restock")
    public ResponseEntity<SweetResponse>restockSweet(@PathVariable Long id,@RequestParam int quantity){
        return ResponseEntity.ok(sweetService.restockSweet(id, quantity));
    }

}
