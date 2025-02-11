package com.Files.DataSync.controller;


import com.Files.DataSync.DTO.Public_Post_Dto;
import com.Files.DataSync.model.Public_Post;
import com.Files.DataSync.service.PublicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchPublicPostById(@PathVariable String id) {
        try {
            Public_Post post = publicService.fetchPublicPostById(id);
            System.out.println(post);
            return ResponseEntity.ok(post);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Post not found"));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadPost(@RequestBody Public_Post_Dto post){
        try {
            String publicPostId = publicService.uploadPost(post);
            // Return JSON response
            return ResponseEntity.ok(Collections.singletonMap("response", publicPostId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to upload post"));
        }
    }

}
