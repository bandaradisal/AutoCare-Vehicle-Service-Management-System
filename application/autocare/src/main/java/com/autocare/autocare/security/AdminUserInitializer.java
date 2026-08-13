package com.autocare.autocare.security;

import com.autocare.autocare.entity.AppUser;
import com.autocare.autocare.repository.AppUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (appUserRepository.existsByUsername("admin")) {
            System.out.println("AutoCare admin account already exists.");
            return;
        }

        String adminPassword = System.getenv("AUTOCARE_ADMIN_PASSWORD");

        if (adminPassword == null || adminPassword.isBlank()) {
            System.out.println(
                "AUTOCARE_ADMIN_PASSWORD is not set. " +
                "Admin account was not created."
            );
            return;
        }

        AppUser admin = new AppUser();

        admin.setUsername("admin");
        admin.setEmail("admin@autocare.lk");
        admin.setPasswordHash(
            passwordEncoder.encode(adminPassword)
        );
        admin.setRole("ADMIN");
        admin.setEnabled(1);

        appUserRepository.save(admin);

        System.out.println(
            "AutoCare ADMIN account created successfully."
        );
    }
}