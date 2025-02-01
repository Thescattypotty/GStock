package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ItemCategoryRequest;
import org.gestionstock.stock.Payload.Response.ItemCategoryResponse;
import org.gestionstock.stock.Service.ItemCategoryService;
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
@RequestMapping("/api/v1/item-categories")
@RequiredArgsConstructor
public class ItemCategoryController {
    
    private final ItemCategoryService itemCategoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid ItemCategoryRequest itemCategoryRequest){
        itemCategoryService.createCategory(itemCategoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") String id ,@RequestBody @Valid ItemCategoryRequest itemCategoryRequest){
        itemCategoryService.updateCategory(id, itemCategoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id){
        itemCategoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCategoryResponse> getCategory(@PathVariable("id") String id){
        return new ResponseEntity<>(itemCategoryService.getCategory(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ItemCategoryResponse>> getCategories(){
        return new ResponseEntity<>(itemCategoryService.getAllCategories(), HttpStatus.OK);
    }
}
