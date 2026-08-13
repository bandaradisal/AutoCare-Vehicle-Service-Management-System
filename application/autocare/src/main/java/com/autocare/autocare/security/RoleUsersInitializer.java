package com.autocare.autocare.security;

import com.autocare.autocare.entity.AppUser;
import com.autocare.autocare.repository.AppUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RoleUsersInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RoleUsersInitializer(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        createUser(
                "advisor",
                "advisor@autocare.lk",
                "SERVICE_ADVISOR",
                "AUTOCARE_ADVISOR_PASSWORD"
        );

        createUser(
                "technician",
                "technician@autocare.lk",
                "TECHNICIAN",
                "AUTOCARE_TECHNICIAN_PASSWORD"
        );

        createUser(
                "inventory",
                "inventory@autocare.lk",
                "INVENTORY_MANAGER",
                "AUTOCARE_INVENTORY_PASSWORD"
        );

        createUser(
                "cashier",
                "cashier@autocare.lk",
                "CASHIER",
                "AUTOCARE_CASHIER_PASSWORD"
        );
    }

    private void createUser(
            String username,
            String email,
            String role,
            String environmentVariable) {

        if (appUserRepository.existsByUsername(username)) {

            System.out.println(
                    "AutoCare user already exists: " + username
            );

            return;
        }

        String password = System.getenv(environmentVariable);

        if (password == null || password.isBlank()) {

            System.out.println(
                    environmentVariable
                            + " is not set. "
                            + username
                            + " account was not created."
            );

            return;
        }

        AppUser user = new AppUser();

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(password)
        );
        user.setRole(role);
        user.setEnabled(1);

        appUserRepository.save(user);

        System.out.println(
                "AutoCare user created successfully: "
                        + username
                        + " [" + role + "]"
        );
    }
}