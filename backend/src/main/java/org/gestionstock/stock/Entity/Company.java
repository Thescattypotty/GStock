package org.gestionstock.stock.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.gestionstock.stock.Enum.EBusinessType;
import org.gestionstock.stock.Enum.ECompanySize;
import org.gestionstock.stock.Enum.EIndustry;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(
    name = "companies",
    indexes = {
        @Index(name = "company_name_index", columnList = "name"),
        @Index(name = "company_country_index", columnList = "country"),
        @Index(name = "company_city_index", columnList = "city"),
        @Index(name = "company_size_index", columnList = "size"),
        @Index(name = "company_industry_index", columnList = "industry"),
        @Index(name = "company_business_type_index", columnList = "businessType"),
        @Index(name = "company_sales_owner_index", columnList = "sales_owner_id"),
    }
)
@EntityListeners(AuditingEntityListener.class)
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ECompanySize size;

    @Enumerated(EnumType.STRING)
    private EIndustry industry;

    @Enumerated(EnumType.STRING)
    private EBusinessType businessType;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String website;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name="sales_owner_id", nullable=false)
    private User salesOwner;

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
