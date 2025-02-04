package org.gestionstock.stock.EntityRepository;

import java.util.UUID;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID>{
    List<InventoryItem> findByCategory(ItemCategory category);
    Optional<InventoryItem> findByName(String name);
}
