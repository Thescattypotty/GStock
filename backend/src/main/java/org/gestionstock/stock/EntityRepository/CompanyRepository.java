package org.gestionstock.stock.EntityRepository;

import java.util.List;
import java.util.UUID;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>{
    List<Company> findBySalesOwner(User salesOwner);
}
