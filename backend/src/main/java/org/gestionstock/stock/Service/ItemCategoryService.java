package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.ItemCategory;
import org.gestionstock.stock.EntityRepository.ItemCategoryRepository;
import org.gestionstock.stock.Exception.ItemCategoryNotFoundException;
import org.gestionstock.stock.IService.IItemCategoryService;
import org.gestionstock.stock.Payload.Mapper.ItemCategoryMapper;
import org.gestionstock.stock.Payload.Request.ItemCategoryRequest;
import org.gestionstock.stock.Payload.Response.ItemCategoryResponse;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemCategoryService implements IItemCategoryService{

    private final ItemCategoryMapper itemCategoryMapper;
    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    @Transactional
    public void createCategory(ItemCategoryRequest itemCategoryRequest) {
        log.info("Creating new category: {}", itemCategoryRequest);
        itemCategoryRepository.save(itemCategoryMapper.toItemCategory(itemCategoryRequest));
        log.info("Created new category: {}", itemCategoryRequest);
    }

    @Override
    @Transactional
    public void updateCategory(String id, ItemCategoryRequest itemCategoryRequest) {
        log.info("Updating category with id: {} with {}", id, itemCategoryRequest);
        ItemCategory itemCategory = itemCategoryRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new ItemCategoryNotFoundException());
        itemCategory.setName(itemCategoryRequest.name());
        itemCategoryRepository.save(itemCategory);
        log.info("Updated category with id: {} with {}", id, itemCategoryRequest);
    }
    
    @Override
    @Transactional
    public void deleteCategory(String id) {
        log.info("Deleting category with id: {}", id);
        if(!itemCategoryRepository.existsById(UUID.fromString(id))) {
            log.info("Category with id: {} not found", id);
            throw new ItemCategoryNotFoundException();
        }
        itemCategoryRepository.deleteById(UUID.fromString(id));
        log.info("Deleted category with id: {}", id);
    }
    
    @Override
    public ItemCategoryResponse getCategory(String id) {
        log.info("Getting category with id: {}", id);
        return itemCategoryRepository.findById(UUID.fromString(id))
            .map(itemCategoryMapper::fromItemCategory)
            .orElseThrow(() -> new ItemCategoryNotFoundException());
    }

    @Override
    public List<ItemCategoryResponse> getAllCategories() {
        log.info("Getting all categories");
        return itemCategoryRepository.findAll()
            .stream()
            .map(itemCategoryMapper::fromItemCategory)
            .collect(Collectors.toList());
    }
}
