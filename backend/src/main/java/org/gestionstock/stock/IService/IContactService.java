package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ContactRequest;
import org.gestionstock.stock.Payload.Response.ContactResponse;

public interface IContactService {
    void createContact(ContactRequest contactRequest);
    void updateContact(String id, ContactRequest contactRequest);
    void deleteContact(String id);
    ContactResponse getContactById(String id);
    List<ContactResponse> getAllContacts();
}
