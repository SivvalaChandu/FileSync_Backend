package com.Files.DataSync.controller;

import com.Files.DataSync.DTO.UserPostDto;
import com.Files.DataSync.model.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
public class WebsocketController {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    @MessageMapping("/send")
//    public UserPostDto sendMessage(@Payload UserPostDto post) {
//
//        System.out.println("Receiver ID: " + post.getUsername());
//        System.out.println("Received Title: " + post.getTitle());
//        System.out.println("Received message: " + post.getMessage());
//        // Forward message directly to the recipient
//        messagingTemplate.convertAndSendToUser(     // /user/{username}/private
//                post.getUsername(),
//                "/private",
//                post
//        );
//        return post;
//    }

//    @MessageMapping("/private-message")
//    public void handlePrivateMessage(@Payload UserPostDto post) {
//        // Log the message details
//        System.out.println("Receiver ID: " + post.getUsername());
//        System.out.println("Received Title: " + post.getTitle());
//        System.out.println("Received message: " + post.getMessage());
//
//        // Send to specific user
//        messagingTemplate.convertAndSendToUser(
//                post.getUsername(),    // recipient username
//                "/private",              // endpoint
//                post                  // message payload
//        );
//    }
}
