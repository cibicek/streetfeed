// PostService.java
package com.proj.streetfeed.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.proj.streetfeed.repository.*;
import lombok.RequiredArgsConstructor;

import com.proj.streetfeed.dto.*;
import com.proj.streetfeed.model.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public Post createPost(PostDTO postDTO, String username) {
        User user = userService.getUserByUsername(username);

        Post post = new Post();
        post.setCaption(postDTO.getCaption());
        post.setLatitude(postDTO.getLatitude());
        post.setLongitude(postDTO.getLongitude());
        post.setHeading(postDTO.getHeading());
        post.setPitch(postDTO.getPitch());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public List<Post> getFeed(String username) {
        User user = userService.getUserByUsername(username);
        List<User> following = new ArrayList<>(user.getFollowing());
        following.add(user);
        return postRepository.findByUserInOrderByCreatedAtDesc(following);
    }
}