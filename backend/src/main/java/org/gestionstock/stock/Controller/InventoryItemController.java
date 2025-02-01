package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.InventoryItemRequest;
import org.gestionstock.stock.Payload.Response.InventoryItemResponse;
import org.gestionstock.stock.Service.InventoryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventory-items")
@RequiredArgsConstructor
public class InventoryItemController {
    
    private final InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<Void> createInventoryItem(@RequestBody @Valid InventoryItemRequest inventoryItemRequest){
        inventoryItemService.createInventoryItem(inventoryItemRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInventoryItem(@PathVariable("id") String id ,@RequestBody @Valid InventoryItemRequest inventoryItemRequest){
        inventoryItemService.updateInventoryItem(id, inventoryItemRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable("id") String id){
        inventoryItemService.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> getInventoryItem(@PathVariable("id") String id){
        return new ResponseEntity<>(inventoryItemService.getInventoryItem(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<InventoryItemResponse>> getInventoryItems(){
        return new ResponseEntity<>(inventoryItemService.getAllInventoryItems(), HttpStatus.OK);
    }
}
