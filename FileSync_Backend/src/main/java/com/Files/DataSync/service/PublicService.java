package com.Files.DataSync.service;

import com.Files.DataSync.DTO.Public_Post_Dto;
import com.Files.DataSync.exception.ResourceNotFoundException;
import com.Files.DataSync.model.Public_Post;
import com.Files.DataSync.repository.Users_Public_Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PublicService {

    private final Users_Public_Post usersPublic_Post;

    public PublicService(Users_Public_Post usersPublic_Post) {
        this.usersPublic_Post = usersPublic_Post;
    }

    public Public_Post fetchPublicPostById(String id) {
        return usersPublic_Post.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    public String uploadPost(Public_Post_Dto post) {
        Public_Post savedPost = new Public_Post();
        savedPost.setId(generateSimpleUniqueId());
        savedPost.setTitle(post.getTitle());
        savedPost.setContent(post.getContent());
        savedPost.setUploadDate(LocalDateTime.now());
        savedPost = usersPublic_Post.save(savedPost);
        return savedPost.getId();
    }

    public String generateSimpleUniqueId() {
        long timestamp = System.currentTimeMillis();
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999); // Adjust range if needed
        return String.format("%d-%d", timestamp, randomNumber);
    }
}
