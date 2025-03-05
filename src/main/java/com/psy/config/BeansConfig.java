package com.psy.config;

import com.github.javafaker.Faker;
import com.psy.models.Doctor;
import com.psy.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.http.HttpHeaders.*;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final UserDetailsService userDetailsService;
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                CONTENT_TYPE,
                ACCEPT,
                AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "DELETE", "PUT", "PATCH"
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    public CommandLineRunner loadData(DoctorRepository doctorRepository) {
//        return args -> {
//            Faker faker = new Faker();
//
//            List<Doctor> doctors = IntStream.range(0, 100)
//                    .mapToObj(i ->
//                            Doctor
//                                    .builder()
//                                    .firstname(faker.name().firstName())
//                                    .lastname(faker.name().lastName())
//                                    .phone(faker.phoneNumber().phoneNumber())
//                                    .email(faker.internet().emailAddress())
//                                    .address(faker.address().fullAddress())
//                                    .city(faker.address().city())
//                                    .gender(faker.demographic().sex())
//                                    .experienceYears(faker.number().numberBetween(1, 40))
//                                    .price(faker.number().numberBetween(60, 120))
//                                    .title(faker.job().title())
//                                    .description(faker.lorem().sentence())
//                                    .build()
//                    )
//                    .toList();
//
//            doctorRepository.saveAll(doctors);
//
//            System.out.println("Fake Doctors added to the database!");
//        };
//    }
}
