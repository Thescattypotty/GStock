package org.gestionstock.stock.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.gestionstock.stock.Entity.ItemCategory;
import org.gestionstock.stock.Payload.Request.ItemCategoryRequest;
import org.gestionstock.stock.Payload.Response.ItemCategoryResponse;
import org.springframework.stereotype.Service;

@Service(value = "ItemCategoryMapper")
public class ItemCategoryMapper {
    
    public ItemCategory toItemCategory(ItemCategoryRequest itemCategoryRequest){
        return ItemCategory.builder()
            .name(itemCategoryRequest.name())
            .build();
    }

    public ItemCategoryResponse fromItemCategory(ItemCategory itemCategory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ItemCategoryResponse(
            itemCategory.getId().toString(),
            itemCategory.getName(),
            itemCategory.getCreatedAt().format(formatter),
            itemCategory.getUpdatedAt().format(formatter),
            itemCategory.getCreatedBy(),
            itemCategory.getUpdatedBy()
        );
    }
}
