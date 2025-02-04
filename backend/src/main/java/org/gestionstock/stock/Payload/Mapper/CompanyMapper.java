package org.gestionstock.stock.Payload.Mapper;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Payload.Request.CompanyRequest;
import org.gestionstock.stock.Payload.Response.CompanyResponse;

public class CompanyMapper {
    
    public static Company toCompany(CompanyRequest companyRequest){
        return Company.builder()
            .name(companyRequest.name())
            .imageUrl(companyRequest.imageUrl())
            .size(companyRequest.size())
            .industry(companyRequest.industry())
            .businessType(companyRequest.businessType())
            .country(companyRequest.country())
            .city(companyRequest.city())
            .address(companyRequest.address())
            .website(companyRequest.website())
            .build();
    }
    
    public static CompanyResponse fromCompany(Company company){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new CompanyResponse(
            company.getId().toString(),
            company.getName(),
            company.getImageUrl(),
            company.getSize(),
            company.getIndustry(),
            company.getBusinessType(),
            company.getCountry(),
            company.getCity(),
            company.getAddress(),
            company.getWebsite(),
            company.getContacts().stream().map(ContactMapper::fromSimplyContact).collect(Collectors.toList()),
            UserMapper.fromUser(company.getSalesOwner()),
            company.getCreatedAt().format(formatter),
            company.getUpdatedAt().format(formatter),
            company.getCreatedBy(),
            company.getUpdatedBy()
        );
    }

    public static CompanyResponse fromSimplyCompany(Company company){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new CompanyResponse(
            company.getId().toString(),
            company.getName(),
            company.getImageUrl(),
            company.getSize(),
            company.getIndustry(),
            company.getBusinessType(),
            company.getCountry(),
            company.getCity(),
            company.getAddress(),
            company.getWebsite(),
            null,
            //company.getContacts().stream().map(ContactMapper::fromContact).collect(Collectors.toList()),
            UserMapper.fromUser(company.getSalesOwner()),
            company.getCreatedAt().format(formatter),
            company.getUpdatedAt().format(formatter),
            company.getCreatedBy(),
            company.getUpdatedBy()
        );
    }

    public static Company toCompany(Company company, CompanyRequest companyRequest){
        company.setName(companyRequest.name());
        company.setImageUrl(companyRequest.imageUrl());
        company.setSize(companyRequest.size());
        company.setIndustry(companyRequest.industry());
        company.setBusinessType(companyRequest.businessType());
        company.setCountry(companyRequest.country());
        company.setCity(companyRequest.city());
        company.setAddress(companyRequest.address());
        company.setWebsite(companyRequest.website());
        return company;
    }

}
