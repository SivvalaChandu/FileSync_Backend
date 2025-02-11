package com.Files.DataSync.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequest {
    private String id;
    private String userId;
    private String title;
    private String message;
    private String type;
    private String time;
}
