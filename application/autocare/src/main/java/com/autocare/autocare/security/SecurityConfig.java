package com.autocare.autocare.security;

import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            // =========================================================
            // AUTHORIZATION RULES
            // =========================================================
            .authorizeHttpRequests(auth -> auth

                // =====================================================
                // INTERNAL FORWARDS + ERROR PAGES
                // =====================================================
                .dispatcherTypeMatchers(
                    DispatcherType.FORWARD,
                    DispatcherType.ERROR
                ).permitAll()


                // =====================================================
                // PUBLIC ACCESS
                // =====================================================
                .requestMatchers(
                    "/login",
                    "/access-denied",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()


                // =====================================================
                // CUSTOMER MANAGEMENT
                // ADMIN + SERVICE ADVISOR
                // =====================================================
                .requestMatchers("/customers/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR"
                )


                // =====================================================
                // VEHICLE MANAGEMENT
                // ADMIN + SERVICE ADVISOR
                // =====================================================
                .requestMatchers("/vehicles/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR"
                )


                // =====================================================
                // SERVICE BOOKINGS
                // ADMIN + SERVICE ADVISOR
                // =====================================================
                .requestMatchers("/bookings/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR"
                )


                // =====================================================
                // TECHNICIAN MANAGEMENT
                // ADMIN ONLY
                // =====================================================
                .requestMatchers("/technicians/**")
                .hasRole("ADMIN")


                // =====================================================
                // SPARE PART INVENTORY
                // ADMIN + INVENTORY MANAGER
                // =====================================================
                .requestMatchers("/spare-parts/**")
                .hasAnyRole(
                    "ADMIN",
                    "INVENTORY_MANAGER"
                )


                // =====================================================
                // INVOICES
                // ADMIN + CASHIER
                // =====================================================
                .requestMatchers("/invoices/**")
                .hasAnyRole(
                    "ADMIN",
                    "CASHIER"
                )


                // =====================================================
                // INVOICE ITEMS
                // ADMIN + INVENTORY MANAGER + CASHIER
                // =====================================================
                .requestMatchers("/invoice-items/**")
                .hasAnyRole(
                    "ADMIN",
                    "INVENTORY_MANAGER",
                    "CASHIER"
                )


                // =====================================================
                // PAYMENTS
                // ADMIN + CASHIER
                // =====================================================
                .requestMatchers("/payments/**")
                .hasAnyRole(
                    "ADMIN",
                    "CASHIER"
                )


                // =====================================================
                // JOB CARDS
                // ADMIN + SERVICE ADVISOR + TECHNICIAN
                // =====================================================
                .requestMatchers("/job-cards/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR",
                    "TECHNICIAN"
                )


                // =====================================================
                // TECHNICIAN NOTES
                // ADMIN + TECHNICIAN
                // =====================================================
                .requestMatchers("/technician-notes/**")
                .hasAnyRole(
                    "ADMIN",
                    "TECHNICIAN"
                )


                // =====================================================
                // SERVICE HISTORY
                // ADMIN + SERVICE ADVISOR + TECHNICIAN
                // =====================================================
                .requestMatchers("/service-history/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR",
                    "TECHNICIAN"
                )


                // =====================================================
                // COMPLAINTS & FEEDBACK
                // ADMIN + SERVICE ADVISOR
                // =====================================================
                .requestMatchers("/complaints-feedback/**")
                .hasAnyRole(
                    "ADMIN",
                    "SERVICE_ADVISOR"
                )


                // =====================================================
                // DIAGNOSTIC SUMMARIES
                // ADMIN + TECHNICIAN
                // =====================================================
                .requestMatchers("/diagnostic-summaries/**")
                .hasAnyRole(
                    "ADMIN",
                    "TECHNICIAN"
                )


                // =====================================================
                // DATABASE CONNECTION TEST
                // ADMIN ONLY
                // =====================================================
                .requestMatchers("/test-databases/**")
                .hasRole("ADMIN")


                // =====================================================
                // EVERYTHING ELSE
                // LOGIN REQUIRED
                // =====================================================
                .anyRequest()
                .authenticated()
            )


            // =========================================================
            // ACCESS DENIED
            // =========================================================
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
            )


            // =========================================================
            // LOGIN
            // =========================================================
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )


            // =========================================================
            // LOGOUT
            // =========================================================
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}