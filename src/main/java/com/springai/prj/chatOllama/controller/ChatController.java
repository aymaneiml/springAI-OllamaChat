package com.springai.prj.chatOllama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private final ChatClient chatClient;


    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return chatClient.prompt()
            .user(message)
            .call()
            .content();
    }

    //streaming real-time response
    @PostMapping("/chat-stream")
    public Flux<String> chatStream(@RequestBody String message) {
        return chatClient.prompt()
            .user(message)
            .stream()
            .content()
            .doOnNext(s -> {
                System.out.println(s);
            });
    }
}