package com.Files.DataSync.service;

import com.Files.DataSync.DTO.PostRequest;
import com.Files.DataSync.DTO.UserPostDto;
import com.Files.DataSync.exception.ResourceNotFoundException;
import com.Files.DataSync.model.User;
import com.Files.DataSync.model.UserPost;
import com.Files.DataSync.repository.UserPostRepository;
import com.Files.DataSync.repository.UserRepository; // Added UserRepository to fetch User
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    private final UserPostRepository postRepository;
    private final UserRepository userRepository; // UserRepository to fetch user data

    @Autowired
    public PostService(UserPostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<UserPostDto> fetchAllPosts(String email) {
        List<UserPostDto> posts = postRepository.findByUserEmail(email).stream()
                .map(UserPostDto::fromEntity)
                .collect(Collectors.toList());
        return posts;
    }

    public UserPost fetchPostById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    public UserPostDto createOrUpdatePost(String postDataJson, MultipartFile file, String postId) {
        try {
            // Map the PostRequest JSON to PostRequest DTO
            ObjectMapper mapper = new ObjectMapper();
            PostRequest postRequest = mapper.readValue(postDataJson, PostRequest.class);

            // Determine if we're creating or updating the post
            UserPost post = (postId != null) ? fetchPostById(postId) : new UserPost();

            // Fetch the user by email (this ensures the user object is set)
            User user = userRepository.findByEmail(postRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found "));

            // Populate post data
            System.out.println("Setting post ID: " + (postId != null ? postId : postRequest.getId()));
            System.out.println("Setting post title: " + postRequest.getTitle());
            System.out.println("Setting post content: " + postRequest.getMessage());
            System.out.println("Setting post type: " + postRequest.getType());
            System.out.println("Setting post upload date: " + postRequest.getTime());

            post.setId((postId != null) ? postId : postRequest.getId());
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getMessage());
            post.setPostType(postRequest.getType());
            post.setUploadDate(postRequest.getTime());
            post.setUser(user); // Set the user to the post

            // Handle file upload if it's provided
            if (file != null && !file.isEmpty()) {
                post.setFileName(file.getOriginalFilename());
                post.setFileData(file.getBytes());
            }

            // Save the post (either create or update based on the postId)
            UserPost savedPost = postRepository.save(post);
            return UserPostDto.fromEntity(savedPost);
        } catch (Exception e) {
            log.error("Error creating or updating post: ", e);
            throw new RuntimeException("Failed to create or update post", e);
        }
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public String generateUniqueId() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString();
        return timestamp + "-" + uuid;
    }
}
