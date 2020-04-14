package com.example.demo.entity;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege viewAdmin
                = createPrivilegeIfNotFound("VIEW_ADMIN");
        Privilege viewCatalog
                = createPrivilegeIfNotFound("VIEW_CATALOG");

        List<Privilege> adminPrivileges = Arrays.asList(viewAdmin, viewCatalog);
        List<Privilege> userPrivileges = Arrays.asList(viewCatalog);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Arrays.asList(adminRole))
                .build();
        Role userRole = roleRepository.findByName("ROLE_USER");
        User user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Arrays.asList(userRole))
                .build();
        userRepository.save(user);
        userRepository.save(admin);

/*        //Valid ISBNs
        "0-596-52068-9"
        "0 512 52068 9"
        "ISBN-10 0-596-52068-9"
        "ISBN-10: 0-596-52068-9"*/

        Book book1 = new Book("0-596-52068-9", "Essentials of negotiation", "Roy J. Lewicki", "1997", "Description", "https://images-na.ssl-images-amazon.com/images/I/51vfmWMKhnL._SX333_BO1,204,203,200_.jpg");
        Book book2 = new Book("0-544-54654-5", "On the Fields of Battle", "John Ostrander", "2005", "Description", "https://covers.openlibrary.org/b/id/869371-M.jpg");
        Book book3 = new Book("0-877-54545-7", "Shades of Gray", "Lisanne Norman", "2010", "Description", "https://covers.openlibrary.org/b/id/6456267-M.jpg");
        Book book4 = new Book("0-552-45468-0", "The House Next Door", "Richie Tankersley Cusick", "2002", "Description", "https://covers.openlibrary.org/b/id/6617186-M.jpg");
        Book book5 = new Book("0-576-54543-7", "Wild Magic", "Tamora Pierce", "2006 ", "Description", "https://covers.openlibrary.org/b/id/760692-M.jpg");
        Book book6 = new Book("0-836-65568-6", "Bringing the mountain home", "SueEllen Campbell", "1996 ", "Description", "https://covers.openlibrary.org/b/id/9325605-M.jpg");

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);
        bookRepository.save(book6);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
