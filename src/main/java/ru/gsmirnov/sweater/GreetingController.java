package ru.gsmirnov.sweater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gsmirnov.sweater.domain.Message;
import ru.gsmirnov.sweater.repository.MessageRepository;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    private final MessageRepository messageRepository;

    @Autowired
    public GreetingController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(/*@RequestParam(name = "name", required = false, defaultValue = "World") String name, */Map<String, Object> model) {
//        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = this.messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
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
