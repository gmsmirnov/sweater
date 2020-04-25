package ru.gsmirnov.sweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gsmirnov.sweater.domain.Message;
import ru.gsmirnov.sweater.domain.User;
import ru.gsmirnov.sweater.repository.MessageRepository;

import java.util.Map;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = this.messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        Message message = new Message(text, tag, user);
        this.messageRepository.save(message);
        Iterable<Message> messages = this.messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messagesByTag;
        if (filter != null && !filter.isEmpty()) {
            messagesByTag = this.messageRepository.findByTag(filter);
        } else {
            messagesByTag = this.messageRepository.findAll();
        }
        model.put("messages", messagesByTag);
        return "main";
    }
}
