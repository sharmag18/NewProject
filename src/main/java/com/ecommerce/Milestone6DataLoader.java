package com.ecommerce;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class Milestone6DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Initializing Milestone 6 data...");
        
        // Create roles
        createRoles();
        
        // Create default users
        createDefaultUsers();
        
        System.out.println("✅ Milestone 6 data initialization completed!");
    }

    private void createRoles() {
        // Create roles if they don't exist
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setDescription("Regular user role");
            roleRepository.save(userRole);
            System.out.println("✅ Created ROLE_USER");
        }

        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setDescription("Administrator role");
            roleRepository.save(adminRole);
            System.out.println("✅ Created ROLE_ADMIN");
        }

        if (!roleRepository.existsByName("ROLE_MODERATOR")) {
            Role moderatorRole = new Role();
            moderatorRole.setName("ROLE_MODERATOR");
            moderatorRole.setDescription("Moderator role");
            roleRepository.save(moderatorRole);
            System.out.println("✅ Created ROLE_MODERATOR");
        }
    }

    private void createDefaultUsers() {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@ecommerce.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setPhone("555-0001");
            adminUser.setCreatedAt(LocalDateTime.now());
            adminUser.setUpdatedAt(LocalDateTime.now());
            adminUser.setIsActive(true);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName("ROLE_ADMIN").orElse(null));
            adminRoles.add(roleRepository.findByName("ROLE_USER").orElse(null));
            adminUser.setRoles(adminRoles);

            userRepository.save(adminUser);
            System.out.println("✅ Created admin user (admin/admin123)");
        }

        // Create test user if not exists
        if (!userRepository.existsByUsername("user")) {
            User testUser = new User();
            testUser.setUsername("user");
            testUser.setEmail("user@ecommerce.com");
            testUser.setPassword(passwordEncoder.encode("user123"));
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setPhone("555-0002");
            testUser.setCreatedAt(LocalDateTime.now());
            testUser.setUpdatedAt(LocalDateTime.now());
            testUser.setIsActive(true);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleRepository.findByName("ROLE_USER").orElse(null));
            testUser.setRoles(userRoles);

            userRepository.save(testUser);
            System.out.println("✅ Created test user (user/user123)");
        }

        // Create moderator user if not exists
        if (!userRepository.existsByUsername("moderator")) {
            User moderatorUser = new User();
            moderatorUser.setUsername("moderator");
            moderatorUser.setEmail("moderator@ecommerce.com");
            moderatorUser.setPassword(passwordEncoder.encode("mod123"));
            moderatorUser.setFirstName("Moderator");
            moderatorUser.setLastName("User");
            moderatorUser.setPhone("555-0003");
            moderatorUser.setCreatedAt(LocalDateTime.now());
            moderatorUser.setUpdatedAt(LocalDateTime.now());
            moderatorUser.setIsActive(true);

            Set<Role> moderatorRoles = new HashSet<>();
            moderatorRoles.add(roleRepository.findByName("ROLE_MODERATOR").orElse(null));
            moderatorRoles.add(roleRepository.findByName("ROLE_USER").orElse(null));
            moderatorUser.setRoles(moderatorRoles);

            userRepository.save(moderatorUser);
            System.out.println("✅ Created moderator user (moderator/mod123)");
        }
    }
} 