package com.Files.DataSync.DTO;

import com.Files.DataSync.model.UserPost;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserPostDto {
//    private String username;
    private String id;
    private String title;
    private String message;
    private String time;
    private String type;
    private String fileName;
//  private LocalDateTime date;

    public static UserPostDto fromEntity(UserPost post) {
        UserPostDto dto = new UserPostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setMessage(post.getContent());
        dto.setType(post.getPostType());
        dto.setTime(post.getUploadDate());
        dto.setFileName(post.getFileName());
        return dto;
    }

}
