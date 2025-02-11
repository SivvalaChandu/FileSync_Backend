package com.Files.DataSync.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Public_Post {
    @Id
    private String id;

    private String title;

    @Lob
    private String content;
    private LocalDateTime uploadDate;
}
