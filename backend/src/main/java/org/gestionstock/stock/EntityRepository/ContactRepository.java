package org.gestionstock.stock.EntityRepository;

import java.util.List;
import java.util.UUID;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>{
    List<Contact> findByCompany(Company company);
    

}
