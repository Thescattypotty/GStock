package org.gestionstock.stock.EntityRepository;

import java.util.Optional;
import java.util.UUID;

import org.gestionstock.stock.Entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {
    Optional<ItemCategory> findByName(String name);
}
