package org.gestionstock.stock.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.Entity.Quote;
import org.gestionstock.stock.Payload.Request.QuoteRequest;
import org.gestionstock.stock.Payload.Response.QuoteResponse;

public class QuoteMapper {
    
    public static Quote toQuote(QuoteRequest quoteRequest, Company company, Contact contact){
        return Quote.builder()
            .quoteName(quoteRequest.quoteName())
            .company(company)
            .contact(contact)
            .description(quoteRequest.description())
            .build();
    }
    public static Quote toQuote(Quote quote, QuoteRequest quoteRequest, Company company, Contact contact){
        quote.setQuoteName(quoteRequest.quoteName());
        quote.setCompany(company);
        quote.setContact(contact);
        quote.setDescription(quoteRequest.description());
        return quote;
    }
    public static QuoteResponse fromQuote(Quote quote){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new QuoteResponse(
            quote.getId().toString(),
            quote.getQuoteName(),
            quote.getDescription(),
            CompanyMapper.fromSimplyCompany(quote.getCompany()),
            ContactMapper.fromSimplyContact(quote.getContact()),
            quote.getProductQuote().stream().map(ProductQuoteMapper::fromProductQuote).toList(),
            quote.getCreatedAt().format(formatter),
            quote.getUpdatedAt().format(formatter),
            quote.getCreatedBy(),
            quote.getUpdatedBy()
        );
    }
}
