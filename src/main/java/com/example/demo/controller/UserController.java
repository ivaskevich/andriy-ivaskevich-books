package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

import static com.example.demo.controller.ControllerUtil.getErrors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User userDetails, BindingResult bindingResult, Model model) {
        boolean hasErrors = false;
        if (userRepository.findByUsername(userDetails.getUsername()) != null) {
            model.addAttribute("usernameError",
                    "User with such username already exist !");
            hasErrors = true;
        }

        if (!userDetails.getUsername().matches("\\b[a-zA-Z][a-zA-Z0-9]{3,}\\b")) {
            model.addAttribute("usernameError",
                    "Valid username characters: a-z, A-Z, 0-9 ! Check your username !");
            hasErrors = true;
        }

        if (userDetails.getPassword().length() > 20 || userDetails.getPassword().length() < 8) {
            model.addAttribute("passwordError",
                    "Password length should bo from 8 to 20 symbols ! Check your password !");
            hasErrors = true;
        }

        if (bindingResult.hasErrors() || hasErrors) {
            Map<String, String> errors = getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        User user = User.builder()
                .username(userDetails.getUsername())
                .password(passwordEncoder().encode(userDetails.getPassword()))
                .roles(Arrays.asList(roleRepository.findByName("ROLE_USER")))
                .build();
        userRepository.save(user);

        return "redirect:/login";
    }
}
