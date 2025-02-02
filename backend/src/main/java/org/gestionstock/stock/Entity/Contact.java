package org.gestionstock.stock.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
    name = "contacts",
    indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_phone", columnList = "phone"),
        @Index(name = "idx_is_customer", columnList = "is_customer"),
        @Index(name = "idx_company_id", columnList = "company_id"),
    }
)
@EntityListeners(AuditingEntityListener.class)
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name" , nullable = false)
    private String fullName;

    @Column(name = "image_url" , nullable = true)
    private String imageUrl;

    @Column(name = "email" ,unique = true, nullable = false)
    private String email;

    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "phone" ,unique = true, nullable = false)
    private String phone;

    @Column(name = "is_customer" ,columnDefinition = "boolean default false")
    private boolean isCustomer;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy;
}
