package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.EntityRepository.CompanyRepository;
import org.gestionstock.stock.EntityRepository.ContactRepository;
import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Exception.CompanyNotFoundException;
import org.gestionstock.stock.Exception.ContactNotFoundException;
import org.gestionstock.stock.Exception.UserNotFoundException;
import org.gestionstock.stock.IService.ICompanyService;
import org.gestionstock.stock.Payload.Mapper.CompanyMapper;
import org.gestionstock.stock.Payload.Request.CompanyRequest;
import org.gestionstock.stock.Payload.Response.CompanyResponse;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService implements ICompanyService{
    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createCompany(CompanyRequest companyRequest) {
        log.info("Creating company: {}", companyRequest);
        Company company = CompanyMapper.toCompany(companyRequest);
        company.setSalesOwner(
            userRepository.findById(UUID.fromString(companyRequest.salesOwnerId()))
                .orElseThrow(() -> new UserNotFoundException())
        );
        log.info("Sales owner: {}", company.getSalesOwner());
        companyRepository.save(company);
        log.info("Company created: {}", company);
    }

    @Override
    @Transactional
    public void updateCompany(String id, CompanyRequest companyRequest) {
        log.info("Updating company: {}", companyRequest);
        Company company = companyRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new CompanyNotFoundException());
        company = CompanyMapper.toCompany(company, companyRequest);
        company.setSalesOwner(
            userRepository.findById(UUID.fromString(companyRequest.salesOwnerId()))
                .orElseThrow(() -> new UserNotFoundException())
        );
        try {
            company.setContacts(
                    contactRepository.findAllById(
                        companyRequest.contactsId()
                            .stream()
                            .map(UUID::fromString)
                            .collect(Collectors.toList())
                    )
                );
        } catch (IllegalArgumentException e) {
            log.info("One or more contacts not found");
            throw new ContactNotFoundException();
        }
        
        companyRepository.save(company);
        log.info("Company updated: {}", company);
    }

    @Override
    @Transactional
    public void deleteCompany(String id) {
        if(!companyRepository.existsById(UUID.fromString(id))) {
            log.info("Company not found");
            throw new CompanyNotFoundException();
        }
        companyRepository.deleteById(UUID.fromString(id));
        log.info("Company deleted: {}", id);
    }

    @Override
    public CompanyResponse getCompanyById(String id) {
        log.info("Getting company by id: {}", id);
        return companyRepository.findById(UUID.fromString(id))
            .map(CompanyMapper::fromCompany)
            .orElseThrow(() -> new CompanyNotFoundException());
    }

    @Override
    public List<CompanyResponse> getAllCompanies() {
        log.info("Getting all companies");
        return companyRepository.findAll()
            .stream()
            .map(CompanyMapper::fromCompany)
            .collect(Collectors.toList());
    }

}
