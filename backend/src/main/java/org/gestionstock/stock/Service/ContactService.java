package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.EntityRepository.CompanyRepository;
import org.gestionstock.stock.EntityRepository.ContactRepository;
import org.gestionstock.stock.Exception.CompanyNotFoundException;
import org.gestionstock.stock.Exception.ContactNotFoundException;
import org.gestionstock.stock.IService.IContactService;
import org.gestionstock.stock.Payload.Mapper.ContactMapper;
import org.gestionstock.stock.Payload.Request.ContactRequest;
import org.gestionstock.stock.Payload.Response.ContactResponse;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService implements IContactService{
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public void createContact(ContactRequest contactRequest) {
        log.info("Creating contact: {}", contactRequest);
        Contact contact = ContactMapper.toContact(contactRequest);
        contact.setCompany(
            companyRepository.findById(UUID.fromString(contactRequest.companyId()))
                .orElseThrow(() -> new CompanyNotFoundException())
        );
        log.info("Contact: {}", contact);
        contactRepository.save(contact);
        log.info("Contact created: {}", contact);
    }

    @Override
    @Transactional
    public void updateContact(String id, ContactRequest contactRequest) {
        log.info("Updating contact: {}", contactRequest);
        Contact contact = contactRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new ContactNotFoundException());
        contact = ContactMapper.toContact(contact, contactRequest);
        contact.setCompany(
            companyRepository.findById(UUID.fromString(contactRequest.companyId()))
                .orElseThrow(() -> new CompanyNotFoundException())
        );
        log.info("Contact: {}", contact);
        contactRepository.save(contact);
        log.info("Contact updated: {}", contact);
    }

    @Override
    @Transactional
    public void deleteContact(String id) {
        log.info("Deleting contact: {}", id);
        if(!contactRepository.existsById(UUID.fromString(id))) {
            log.info("Contact not found: {}", id);
            throw new ContactNotFoundException();
        }
        contactRepository.deleteById(UUID.fromString(id));
        log.info("Contact deleted: {}", id);
    }
    
    @Override
    public ContactResponse getContactById(String id) {
        log.info("Getting contact: {}", id);
        return contactRepository.findById(UUID.fromString(id))
            .map(ContactMapper::fromContact)
            .orElseThrow(() -> new ContactNotFoundException());
    }
    
    @Override
    public List<ContactResponse> getAllContacts() {
        log.info("Getting all contacts");
        return contactRepository.findAll()
            .stream()
            .map(ContactMapper::fromContact)
            .collect(Collectors.toList());
    }

}
