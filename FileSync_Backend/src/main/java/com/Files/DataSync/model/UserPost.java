package com.Files.DataSync.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(
        name = "user_posts",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_id")
        }
)
public class UserPost {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    private String title;
    @Lob
    @Column(name = "content")
    private String content;
    private String uploadDate;
    private String fileName;
    private String postType;

    @Lob
    @Column(name = "file_data", columnDefinition="LONGBLOB")
    private byte[] fileData;
}
