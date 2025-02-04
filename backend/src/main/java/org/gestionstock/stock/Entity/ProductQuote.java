package org.gestionstock.stock.Entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_quotes")
public class ProductQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_quote_id", nullable = false, updatable = false)
    private InventoryItem item;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "marge_gain",nullable = false)
    private BigDecimal margeDeGain;

    @Column(name = "price_HT", nullable = false)
    private BigDecimal priceHT;

    @Column(name = "price_TTC", nullable = false)
    private BigDecimal priceTTC;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;
}
