package org.gestionstock.stock.EntityRepository;

import java.util.UUID;

import org.gestionstock.stock.Entity.ProductQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuoteRepository extends JpaRepository<ProductQuote, UUID>{
    
}
