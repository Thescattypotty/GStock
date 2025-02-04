package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ProductQuote;
import org.gestionstock.stock.Entity.Quote;
import org.gestionstock.stock.EntityRepository.CompanyRepository;
import org.gestionstock.stock.EntityRepository.ContactRepository;
import org.gestionstock.stock.EntityRepository.InventoryItemRepository;
import org.gestionstock.stock.EntityRepository.ProductQuoteRepository;
import org.gestionstock.stock.EntityRepository.QuoteRepository;
import org.gestionstock.stock.Exception.CompanyNotFoundException;
import org.gestionstock.stock.Exception.ContactNotFoundException;
import org.gestionstock.stock.Exception.InventoryItemNotFoundException;
import org.gestionstock.stock.Exception.ProductQuoteNotFoundException;
import org.gestionstock.stock.Exception.QuoteNotFoundException;
import org.gestionstock.stock.IService.IQuoteService;
import org.gestionstock.stock.Payload.Mapper.ProductQuoteMapper;
import org.gestionstock.stock.Payload.Mapper.QuoteMapper;
import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.gestionstock.stock.Payload.Request.QuoteRequest;
import org.gestionstock.stock.Payload.Response.QuoteResponse;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService implements IQuoteService{
    private final QuoteRepository quoteRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final ProductQuoteRepository productQuoteRepository;

    private final ProductQuoteService productQuoteService;

    @Override
    @Transactional
    public void createQuote(QuoteRequest quoteRequest) {
        Company company = companyRepository.findById(UUID.fromString(quoteRequest.companyId()))
            .orElseThrow(() -> new CompanyNotFoundException());

        Contact contact = contactRepository.findById(UUID.fromString(quoteRequest.contactId()))
            .orElseThrow(() -> new ContactNotFoundException());

        Quote quote = QuoteMapper.toQuote(quoteRequest, company, contact);

        List<ProductQuote> productQuotes = quoteRequest.productQuoteRequest().stream()
            .map(productQuoteRequest -> {
                InventoryItem item = inventoryItemRepository.findById(UUID.fromString(productQuoteRequest.itemId()))
                    .orElseThrow(() -> new InventoryItemNotFoundException());
                return productQuoteService.createProductQuote(ProductQuoteMapper.toProductQuote(productQuoteRequest, item));
            })
            .collect(Collectors.toList());
        
        quote.setProductQuote(productQuotes);
        quoteRepository.save(quote);
    }

    @Override
    @Transactional
    public void updateQuote(String id, QuoteRequest quoteRequest) {
        Quote quote = quoteRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new QuoteNotFoundException());
        Company company = companyRepository.findById(UUID.fromString(quoteRequest.companyId()))
            .orElseThrow(() -> new CompanyNotFoundException());

        Contact contact = contactRepository.findById(UUID.fromString(quoteRequest.contactId()))
            .orElseThrow(() -> new ContactNotFoundException());

        quote.getProductQuote().clear();
        List<ProductQuote> productQuotes = quoteRequest.productQuoteRequest().stream()
            .map(productQuoteRequest -> {
                InventoryItem item = inventoryItemRepository.findById(UUID.fromString(productQuoteRequest.itemId()))
                    .orElseThrow(() -> new InventoryItemNotFoundException());
                return productQuoteService.createProductQuote(ProductQuoteMapper.toProductQuote(productQuoteRequest, item));
            })
            .collect(Collectors.toList());
        
        quote.getProductQuote().addAll(productQuotes);

        quote = QuoteMapper.toQuote(quote, quoteRequest, company, contact);
        quoteRepository.save(quote);
    }
    
    @Override
    @Transactional
    public void deleteQuote(String id) {
        if(!quoteRepository.existsById(UUID.fromString(id))) {
            throw new QuoteNotFoundException();
        }
        quoteRepository.deleteById(UUID.fromString(id));
    }

    @Override
    @Transactional
    public void updateProductQuote(String id, ProductQuoteRequest productQuoteRequest) {
        ProductQuote productQuote = productQuoteRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new ProductQuoteNotFoundException());
        InventoryItem item = inventoryItemRepository.findById(UUID.fromString(productQuoteRequest.itemId()))
            .orElseThrow(() -> new InventoryItemNotFoundException());
        productQuote = productQuoteService.createProductQuote(productQuoteRequest, item);
        productQuoteRepository.save(productQuote);
    }

    @Override
    @Transactional
    public void deleteProductQuote(String id) {
        if(!productQuoteRepository.existsById(UUID.fromString(id))) {
            throw new ProductQuoteNotFoundException();
        }
        productQuoteRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public QuoteResponse getQuote(String id) {
        return quoteRepository.findById(UUID.fromString(id))
            .map(QuoteMapper::fromQuote)
            .orElseThrow(() -> new QuoteNotFoundException());
    }

    @Override
    public List<QuoteResponse> getQuotes() {
        return quoteRepository.findAll()
            .stream()
            .map(QuoteMapper::fromQuote)
            .collect(Collectors.toList());
    }
}
