package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.CompanyRequest;
import org.gestionstock.stock.Payload.Response.CompanyResponse;

public interface ICompanyService {
    void createCompany(CompanyRequest companyRequest);
    void updateCompany(String id, CompanyRequest companyRequest);
    void deleteCompany(String id);
    CompanyResponse getCompanyById(String id);
    List<CompanyResponse> getAllCompanies();
}
