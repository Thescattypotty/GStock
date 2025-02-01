package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ItemCategoryRequest;
import org.gestionstock.stock.Payload.Response.ItemCategoryResponse;

public interface IItemCategoryService {
    void createCategory(ItemCategoryRequest itemCategoryRequest);
    void updateCategory(String id , ItemCategoryRequest itemCategoryRequest);
    void deleteCategory(String id);
    List<ItemCategoryResponse> getAllCategories();
    ItemCategoryResponse getCategory(String id);
}
