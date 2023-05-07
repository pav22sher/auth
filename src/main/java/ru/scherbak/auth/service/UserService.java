package ru.scherbak.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scherbak.auth.entity.Role;
import ru.scherbak.auth.entity.Usr;
import ru.scherbak.auth.repository.RoleRepository;
import ru.scherbak.auth.repository.UsrRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsrRepository usrRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void init() {
        Role userRole = new Role("ROLE_USER");
        Usr user = Usr.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .email("user@ya.ru")
                .roles(List.of(userRole))
                .build();
        Usr exist1 = usrRepository.findByUsername(user.getUsername());
        if(exist1 == null) {
            roleRepository.saveAndFlush(userRole);
            usrRepository.saveAndFlush(user);
        }
        Role adminRole = new Role("ROLE_ADMIN");
        Usr admin = Usr.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .email("admin@ya.ru")
                .roles(List.of(adminRole))
                .build();
        Usr exist2 = usrRepository.findByUsername(admin.getUsername());
        if(exist2 == null) {
            roleRepository.saveAndFlush(adminRole);
            usrRepository.saveAndFlush(admin);
        }
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usr usr = usrRepository.findByUsername(username);
        if(usr == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return usr;
    }

}
