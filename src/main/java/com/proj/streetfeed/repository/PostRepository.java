// PostRepository.java
package com.proj.streetfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proj.streetfeed.model.Post;
import com.proj.streetfeed.model.User;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
}