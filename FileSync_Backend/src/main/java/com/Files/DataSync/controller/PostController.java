package com.Files.DataSync.controller;

import com.Files.DataSync.DTO.UserPostDto;
import com.Files.DataSync.exception.ApiError;
import com.Files.DataSync.exception.ResourceNotFoundException;
import com.Files.DataSync.model.UserPost;
import com.Files.DataSync.service.PostService;
import com.Files.DataSync.util.MimeTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

//    remove path variable use request body
    @GetMapping("/all/{email}")
    public ResponseEntity<List<UserPostDto>> fetchAllPosts(@PathVariable String email) {
        List<UserPostDto> posts = postService.fetchAllPosts(email);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPost> fetchPostById(@PathVariable String id) {
        UserPost post = postService.fetchPostById(id);
        return ResponseEntity.ok(post);
    }

//    use MulitpartFile to upload image and other files also use websocket to send the file to the server
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPost(
            @RequestPart("postData") String postDataJson,
            @RequestPart(value = "file", required = false) MultipartFile file){
        try {
            System.out.println("Posting data");
            UserPostDto savedPost = postService.createOrUpdatePost(postDataJson, file,null);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            log.error("Error in upload: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Failed to create post: " + e.getMessage()));
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> uploadPost(
            @RequestPart("postData") String postDataJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable String postId){
        try {
            System.out.println("updating existing post data");
            UserPostDto savedPost = postService.createOrUpdatePost(postDataJson, file,postId);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            log.error("Error in upload: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Failed to create post: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        try {
            UserPost post = postService.fetchPostById(id);
            if (post.getFileData() == null) {
                return ResponseEntity.badRequest().body(new ApiError("No file available for this post"));
            }
            System.out.println(post.getPostType());
            System.out.println(post.getFileName());
            System.out.println("File size: " + post.getFileData().length);
            System.out.println("Content-Type: " + post.getPostType());


            String mimeType = MimeTypeUtil.getMimeType(post.getPostType());
            String contentDisposition = "attachment; filename=\"" + post.getFileName() + "\"";
            System.out.println("Content-Disposition: " + contentDisposition);


            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(post.getFileData());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("Error downloading file: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
