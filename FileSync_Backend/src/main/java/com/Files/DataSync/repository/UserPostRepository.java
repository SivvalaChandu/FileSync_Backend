package com.Files.DataSync.repository;

import com.Files.DataSync.model.User;
import com.Files.DataSync.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, String> {

    @Query("SELECT p FROM UserPost p WHERE p.user.email = :email")
    List<UserPost> findByUserEmail(@Param("email") String email);
}
