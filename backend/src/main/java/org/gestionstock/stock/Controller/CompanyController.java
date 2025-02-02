package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.CompanyRequest;
import org.gestionstock.stock.Payload.Response.CompanyResponse;
import org.gestionstock.stock.Service.CompanyService;
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
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> createCompany(@RequestBody @Valid CompanyRequest companyRequest){
        companyService.createCompany(companyRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable("id") String id,@RequestBody @Valid CompanyRequest companyRequest){
        companyService.updateCompany(id, companyRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") String id){
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable("id") String id){
        return new ResponseEntity<>(companyService.getCompanyById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }
}
