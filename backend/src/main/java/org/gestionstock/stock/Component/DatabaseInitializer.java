package org.gestionstock.stock.Component;

import java.util.Set;

import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Enum.ERole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initUsers();
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
}
