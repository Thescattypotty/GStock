package org.gestionstock.stock.Component;

import java.math.BigDecimal;
import java.util.Set;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ItemCategory;
import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.EntityRepository.InventoryItemRepository;
import org.gestionstock.stock.EntityRepository.ItemCategoryRepository;
import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Enum.ERole;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initUsers();
        initInventory();
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
                .costPrice(BigDecimal.valueOf(1500))
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
                .costPrice(BigDecimal.valueOf(20))
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

    private void setAuthenticatedUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
