package org.gestionstock.stock.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Builder
@Getter
@Setter
@Entity
@Table(
    name = "inventory_items",
    indexes = {
        @Index(name = "inventory_items_name_index", columnList = "name"),
        @Index(name = "inventory_items_category_id_index", columnList = "category_id"),
        @Index(name = "inventory_items_created_by_index", columnList = "created_by"),
        @Index(name = "inventory_items_updated_by_index", columnList = "updated_by")
    }
)
@EntityListeners(AuditingEntityListener.class)
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ItemCategory category;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by" ,nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by",nullable = false)
    private String updatedBy;

}
