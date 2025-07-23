package kh.edu.cstad.springa_4_bankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "CUSTOMER";

//    @Bean
//    public InMemoryUserDetailsManager configureUsers(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails admin  = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("ph28"))
//                .roles("ADMIN")
//                .build();
//
//        manager.createUser(admin);
//
//        UserDetails staff  = User.builder()
//                .username("staff")
//                .password(passwordEncoder.encode("ph28"))
//                .roles("STAFF")
//                .build();
//
//        manager.createUser(staff);
//
//        UserDetails customer  = User.builder()
//                .username("customer")
//                .password(passwordEncoder.encode("ph28"))
//                .roles("CUSTOMER")
//                .build();
//
//        manager.createUser(customer);
//
//        return manager;
//    }


    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthProvider;
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
                        .anyRequest()
                        .authenticated());

        http.httpBasic(Customizer.withDefaults());

        http.formLogin(formLogin -> formLogin.disable());

        http.csrf(token -> token.disable());

        http.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
