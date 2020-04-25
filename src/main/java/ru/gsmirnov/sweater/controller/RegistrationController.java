package ru.gsmirnov.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gsmirnov.sweater.domain.Role;
import ru.gsmirnov.sweater.domain.User;
import ru.gsmirnov.sweater.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        String result = "redirect:/login";
        User userFromDb = this.userRepository.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exists!");
            result = "registration";
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            this.userRepository.save(user);
        }
        return result;
    }
}
