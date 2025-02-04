package org.gestionstock.stock.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.Payload.Request.ContactRequest;
import org.gestionstock.stock.Payload.Response.ContactResponse;

public class ContactMapper {

    public static Contact toContact(ContactRequest contactRequest){
        return Contact.builder()
            .fullName(contactRequest.fullName())
            .imageUrl(contactRequest.imageUrl())
            .email(contactRequest.email())
            .title(contactRequest.title())
            .phone(contactRequest.phone())
            .isCustomer(false)
            .build();
    }

    public static ContactResponse fromContact(Contact contact){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ContactResponse(
            contact.getId().toString(),
            contact.getFullName(),
            contact.getImageUrl(),
            contact.getEmail(),
            contact.getTitle(),
            contact.getPhone(),
            contact.isCustomer(),
            CompanyMapper.fromSimplyCompany(contact.getCompany()),
            contact.getCreatedBy(),
            contact.getUpdatedBy(),
            contact.getCreatedAt().format(formatter),
            contact.getUpdatedAt().format(formatter)
        );
    }

    public static ContactResponse fromSimplyContact(Contact contact){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ContactResponse(
            contact.getId().toString(),
            contact.getFullName(),
            contact.getImageUrl(),
            contact.getEmail(),
            contact.getTitle(),
            contact.getPhone(),
            contact.isCustomer(),
            null,
            //CompanyMapper.fromSimplyCompany(contact.getCompany()),
            contact.getCreatedBy(),
            contact.getUpdatedBy(),
            contact.getCreatedAt().format(formatter),
            contact.getUpdatedAt().format(formatter)
        );
    }

    public static Contact toContact(Contact contact, ContactRequest contactRequest){
        contact.setFullName(contactRequest.fullName());
        contact.setImageUrl(contactRequest.imageUrl());
        contact.setEmail(contactRequest.email());
        contact.setTitle(contactRequest.title());
        contact.setPhone(contactRequest.phone());
        return contact;
    }
}
