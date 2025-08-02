package kh.edu.cstad.springa_4_bankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class KeyCloakSecurityConfig {

//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "CUSTOMER";

//    @Bean
//    public DaoAuthenticationProvider daoAuthProvider() {
//        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider(userDetailsService);
//        daoAuthProvider.setPasswordEncoder(passwordEncoder);
//
//        return daoAuthProvider;
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeyCloak() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {

            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");

            return  roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }

    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(endpoint ->
                endpoint
                        .requestMatchers(HttpMethod.POST,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN,ROLE_STAFF)
                        .requestMatchers(HttpMethod.DELETE,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN,ROLE_STAFF,ROLE_CUSTOMER)
                        .requestMatchers(HttpMethod.GET,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN,ROLE_STAFF,ROLE_CUSTOMER)
                        .requestMatchers("api/v1/accounts/**").hasAnyRole(ROLE_ADMIN,ROLE_CUSTOMER)
                        .requestMatchers("media/**").permitAll()
                        .requestMatchers("/api/v1/medias/**").permitAll()
                        .anyRequest()
                        .authenticated());

        http.httpBasic(Customizer.withDefaults());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        http.csrf(AbstractHttpConfigurer::disable);

//        http.formLogin(formLogin -> formLogin.disable());
//        http.csrf(token -> token.disable());

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
