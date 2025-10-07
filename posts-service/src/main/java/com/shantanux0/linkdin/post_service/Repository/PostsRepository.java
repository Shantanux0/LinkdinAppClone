package com.shantanux0.linkdin.post_service.Repository;


import com.shantanux0.linkdin.post_service.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
