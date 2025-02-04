package org.gestionstock.stock.EntityRepository;

import java.util.List;
import java.util.UUID;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.Entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID>{
    List<Quote> findByCompany(Company company);
    List<Quote> findByContact(Contact contact);
}
