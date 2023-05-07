package ru.scherbak.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.scherbak.auth.service.UserService;

import javax.annotation.PostConstruct;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
                .formLogin();
        // Disable CSRF protection (this is required to use the console with Spring Security)
        http.csrf().disable();

        // Allow frames with same origin to be displayed (this is required to use the console with Spring Security)
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    //In Memory
    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = getUser();
        UserDetails admin = getAdmin();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

    //In DB
    /*@Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        UserDetails user = getUser();
        if (!jdbcUserDetailsManager.userExists(user.getUsername())) {
            jdbcUserDetailsManager.createUser(user);
        }
        UserDetails admin = getAdmin();
        if (!jdbcUserDetailsManager.userExists(admin.getUsername())) {
            jdbcUserDetailsManager.createUser(admin);
        }
        return jdbcUserDetailsManager;
    }*/

    private UserDetails getUser() {
        return User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
    }

    private UserDetails getAdmin() {
        return User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
    }

    @Autowired
    private UserService userService;

    @PostConstruct
    private void init() {
        userService.init();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //In DB custom
    @Bean
    public DaoAuthenticationProvider users() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}
