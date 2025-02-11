package com.Files.DataSync.repository;

import com.Files.DataSync.model.Public_Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_Public_Post extends JpaRepository<Public_Post, String> {
}
