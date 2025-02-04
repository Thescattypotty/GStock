package org.gestionstock.stock.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.gestionstock.stock.Entity.Company;
import org.gestionstock.stock.Entity.Contact;
import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ItemCategory;
import org.gestionstock.stock.Entity.Quote;
import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.EntityRepository.CompanyRepository;
import org.gestionstock.stock.EntityRepository.ContactRepository;
import org.gestionstock.stock.EntityRepository.InventoryItemRepository;
import org.gestionstock.stock.EntityRepository.ItemCategoryRepository;
import org.gestionstock.stock.EntityRepository.QuoteRepository;
import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Enum.EBusinessType;
import org.gestionstock.stock.Enum.ECompanySize;
import org.gestionstock.stock.Enum.EIndustry;
import org.gestionstock.stock.Enum.ERole;
import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.gestionstock.stock.Service.ProductQuoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner{
    private final UserRepository userRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final QuoteRepository quoteRepository;
    private final ProductQuoteService productQuoteService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initUsers();
        initInventory();
        initCompaniesAndContacts();
        initQuotes();
    }

    @Transactional
    public void initUsers() {
    if (userRepository.findAll().stream().findAny().isEmpty()) {
        User user1 = User.builder()
                .username("admin")
                .firstName("John")
                .lastName("Doe")
                .address("123 rue de Paris")
                .phone("0123456789")
                .imageUrl("https://i.pravatar.cc/150?img=48")
                .email("admin@example.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(Set.of(ERole.SUPER_ADMINISTRATEUR))
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .username("manager")
                .firstName("Jane")
                .lastName("Doe")
                .address("124 rue de Paris")
                .phone("0123456788")
                .imageUrl("https://i.pravatar.cc/150?img=42")
                .email("manager@example.com")
                .password(passwordEncoder.encode("manager123"))
                .roles(Set.of(ERole.GESTIONNAIRE_STOCK))
                .build();
        userRepository.save(user2);

        User user3 = User.builder()
                .username("client")
                .firstName("Paul")
                .lastName("Doe")
                .address("124 rue de Bruxelle")
                .phone("0123456787")
                .imageUrl("https://i.pravatar.cc/150?img=12")
                .email("client@example.com")
                .password(passwordEncoder.encode("client123"))
                .roles(Set.of(ERole.CLIENT_FINAL))
                .build();
        userRepository.save(user3);

        }
    }

    @Transactional
    public void initInventory() {
        if(itemCategoryRepository.findAll().stream().findAny().isEmpty()) {
            setAuthenticatedUser("admin");
            ItemCategory itemCategory1 = ItemCategory.builder()
                .name("Electronics")
                .build();
            itemCategoryRepository.save(itemCategory1);

            ItemCategory itemCategory2 = ItemCategory.builder()
                .name("Clothing")
                .build();
            itemCategoryRepository.save(itemCategory2);

            ItemCategory itemCategory3 = ItemCategory.builder()
                .name("Books")
                .build();

            itemCategoryRepository.save(itemCategory3);

            InventoryItem inventoryItem1 = InventoryItem.builder()
                .name("Laptop")
                .description("Dell XPS 15")
                .quantity(10L)
                .costPrice(BigDecimal.valueOf(10))
                .tva(BigDecimal.valueOf(0.15))
                .category(itemCategory1)
                .createdBy("admin")
                .updatedBy("admin")
                .build();
            inventoryItemRepository.save(inventoryItem1);

            InventoryItem inventoryItem2 = InventoryItem.builder()
                .name("T-shirt")
                .description("Blue T-shirt")
                .quantity(100L)
                .costPrice(BigDecimal.valueOf(10))
                .tva(BigDecimal.valueOf(0.20))
                .category(itemCategory2)
                .createdBy("admin")
                .updatedBy("admin")
                .build();
            inventoryItemRepository.save(inventoryItem2);

            InventoryItem inventoryItem3 = InventoryItem.builder()
                .name("Java Programming")
                .description("Java Programming for beginners")
                .quantity(50L)
                .costPrice(BigDecimal.valueOf(10))
                .tva(BigDecimal.valueOf(0.40))
                .category(itemCategory3)
                .createdBy("admin")
                .updatedBy("admin")
                .build();

            inventoryItemRepository.save(inventoryItem3);
        }
    }

    @Transactional
    public void initCompaniesAndContacts(){
        if(companyRepository.findAll().stream().findAny().isEmpty()){
            setAuthenticatedUser("admin");
            Company company1 = Company.builder()
                .name("Company 1")
                .imageUrl("https://i.pravatar.cc/150?img=1")
                .size(ECompanySize.SMALL)
                .industry(EIndustry.TECHNOLOGY)
                .businessType(EBusinessType.B2B)
                .country("Maroc")
                .city("Casablanca")
                .address("123 rue de Paris")
                .website("https://www.company1.com")
                .salesOwner(userRepository.findByUsername("manager").get())
                .build();
            companyRepository.save(company1);

            Company company2 = Company.builder()
                .name("Company 2")
                .imageUrl("https://i.pravatar.cc/150?img=2")
                .size(ECompanySize.MEDIUM)
                .industry(EIndustry.FINANCE)
                .businessType(EBusinessType.B2C)
                .country("Maroc")
                .city("Rabat")
                .address("124 rue de Paris")
                .website("https://www.company2.com")
                .salesOwner(userRepository.findByUsername("manager").get())
                .build();
            companyRepository.save(company2);

            Company company3 = Company.builder()
                .name("Company 3")
                .imageUrl("https://i.pravatar.cc/150?img=3")
                .size(ECompanySize.LARGE)
                .industry(EIndustry.HEALTH)
                .businessType(EBusinessType.B2B)
                .country("Maroc")
                .city("Tanger")
                .address("125 rue de Paris")
                .website("https://www.company3.com")
                .salesOwner(userRepository.findByUsername("manager").get())
                .build();
            companyRepository.save(company3);

            Contact contact1 = Contact.builder()
                .fullName("John Doe")
                .imageUrl("https://i.pravatar.cc/150?img=4")
                .email("johnDoe@gmail.com")
                .title("CEO")
                .phone("0123456789")
                .isCustomer(true)
                .company(company1)
                .build();
            contactRepository.save(contact1);

            Contact contact2 = Contact.builder()
                .fullName("Jane Doe")
                .imageUrl("https://i.pravatar.cc/150?img=5")
                .email("jahnDoe@gmail.com")
                .title("CTO")
                .phone("0123456788")
                .isCustomer(false)
                .company(company2)
                .build();
            contactRepository.save(contact2);

            Contact contact3 = Contact.builder()
                .fullName("Paul Doe")
                .imageUrl("https://i.pravatar.cc/150?img=6")
                .email("PaulDoe@gmail.com")
                .title("CFO")
                .phone("0123456787")
                .isCustomer(true)
                .company(company3)
                .build();
            contactRepository.save(contact3);

            Contact contact4 = Contact.builder()
                .fullName("Alice Doe")
                .imageUrl("https://i.pravatar.cc/150?img=7")
                .email("AliceDoe@gmail.com")
                .title("COO")
                .phone("0123456786")
                .isCustomer(false)
                .company(company1)
                .build();
            contactRepository.save(contact4);

            Contact contact5 = Contact.builder()
                .fullName("Bob Doe")
                .imageUrl("https://i.pravatar.cc/150?img=8")
                .email("BobDoe@gmail.com")
                .title("CIO")
                .phone("0123456785")
                .isCustomer(true)
                .company(company2)
                .build();
            contactRepository.save(contact5);

            Contact contact6 = Contact.builder()
                .fullName("Eva Doe")
                .imageUrl("https://i.pravatar.cc/150?img=9")
                .email("EvaDoe@gmail.com")
                .title("CMO")
                .phone("0123456784")
                .isCustomer(false)
                .company(company3)
                .build();
            contactRepository.save(contact6);

            Contact contact7 = Contact.builder()
                .fullName("Sam Doe")
                .imageUrl("https://i.pravatar.cc/150?img=10")
                .email("SamDoe@gmail.com")
                .title("CPO")
                .phone("0123456783")
                .isCustomer(true)
                .company(company1)
                .build();
            contactRepository.save(contact7);

            Contact contact8 = Contact.builder()
                .fullName("Sara Doe")
                .imageUrl("https://i.pravatar.cc/150?img=11")
                .email("SaraDoe@gmail.com")
                .title("CFO")
                .phone("0123456782")
                .isCustomer(false)
                .company(company2)
                .build();
            contactRepository.save(contact8);

            Contact contact9 = Contact.builder()
                .fullName("Tom Doe")
                .imageUrl("https://i.pravatar.cc/150?img=12")
                .email("TomDoe@gmail.com")
                .title("CEO")
                .phone("0123456781")
                .isCustomer(true)
                .company(company3)
                .build();
            contactRepository.save(contact9);
            
        }
    }

    @Transactional
    public void initQuotes(){
        if(quoteRepository.findAll().stream().findAny().isEmpty()){
            setAuthenticatedUser("manager");
            Company company = companyRepository.findByName("Company 1").get();
            InventoryItem item1 = inventoryItemRepository.findByName("Laptop").get();
            InventoryItem item2 = inventoryItemRepository.findByName("T-shirt").get();
            InventoryItem item3 = inventoryItemRepository.findByName("Java Programming").get();

            Quote quote1 = Quote.builder()
                .quoteName("Quote 1")
                .company(company)
                .contact(company.getContacts().get(0))
                .description("Quote 1 description")
                .productQuote(
                    List.of(
                        productQuoteService.createProductQuote(
                            new ProductQuoteRequest("", 2L, 0.8, 0.0),
                            item1
                        ),
                        productQuoteService.createProductQuote(
                            new ProductQuoteRequest("", 1L, 0.7, 0.0),
                            item2
                        ),
                        productQuoteService.createProductQuote(
                            new ProductQuoteRequest("", 1L, 0.3, 0.0),
                            item3
                        )
                    )
                )
                .build();
            quoteRepository.save(quote1);
        }
    }

    private void setAuthenticatedUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
