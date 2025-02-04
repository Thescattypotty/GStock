package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.gestionstock.stock.Payload.Request.QuoteRequest;
import org.gestionstock.stock.Payload.Response.QuoteResponse;
import org.gestionstock.stock.Service.QuoteService;
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
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
public class QuoteController {
    
    private final QuoteService quoteService;

    @PostMapping
    public ResponseEntity<Void> createQuote(@RequestBody @Valid QuoteRequest quoteRequest) {
        quoteService.createQuote(quoteRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuote(@PathVariable("id") String id, @RequestBody @Valid QuoteRequest quoteRequest) {
        quoteService.updateQuote(id, quoteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable("id") String id) {
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<Void> updateProductQuote(@PathVariable("id") String id, @RequestBody @Valid ProductQuoteRequest productQuoteRequest) {
        quoteService.updateProductQuote(id, productQuoteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProductQuote(@PathVariable("id") String id) {
        quoteService.deleteProductQuote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable("id") String id) {
        return new ResponseEntity<>(quoteService.getQuote(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getQuotes() {
        return new ResponseEntity<>(quoteService.getQuotes(), HttpStatus.OK);
    }
}
