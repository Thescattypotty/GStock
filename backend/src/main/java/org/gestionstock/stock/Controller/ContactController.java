package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ContactRequest;
import org.gestionstock.stock.Payload.Response.ContactResponse;
import org.gestionstock.stock.Service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody @Valid ContactRequest contactRequest){
        contactService.createContact(contactRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContact(@PathVariable("id") String id,@RequestBody @Valid ContactRequest contactRequest){
        contactService.updateContact(id, contactRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable("id") String id){
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContact(@PathVariable("id") String id){
        return new ResponseEntity<>(contactService.getContactById(id), HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<ContactResponse>> getContacts(){
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }
}
