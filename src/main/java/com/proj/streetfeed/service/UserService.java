// UserService.java
package com.proj.streetfeed.service;

import com.proj.streetfeed.dto.UserDTO;
import com.proj.streetfeed.model.User;
import com.proj.streetfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

     @Transactional
     public User registerNewUser(UserDTO userDTO) {
          // Check if username already exists
          if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
               throw new RuntimeException("Username already exists");
          }

          // Create new user
          User newUser = new User();
          newUser.setUsername(userDTO.getUsername());
          newUser.setEmail(userDTO.getEmail());
          // Encode password before storing
          newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
          newUser.setProfilePicture(userDTO.getProfilePicture());

          return userRepository.save(newUser);
     }

     @Transactional(readOnly = true)
     public User getUserByUsername(String username) {
          return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
     }

     @Transactional
     public User updateUser(String username, UserDTO userDTO) {
          User existingUser = getUserByUsername(username);

          // Update fields that are allowed to be changed
          if (userDTO.getEmail() != null) {
               existingUser.setEmail(userDTO.getEmail());
          }
          if (userDTO.getProfilePicture() != null) {
               existingUser.setProfilePicture(userDTO.getProfilePicture());
          }

          return userRepository.save(existingUser);
     }

     @Transactional
     public void followUser(String followerUsername, String followedUsername) {
          User follower = getUserByUsername(followerUsername);
          User followed = getUserByUsername(followedUsername);

          // Prevent self-following
          if (follower.equals(followed)) {
               throw new RuntimeException("Cannot follow yourself");
          }

          // Add to following set
          follower.getFollowing().add(followed);
          // Add to followers set
          followed.getFollowers().add(follower);

          userRepository.save(follower);
          userRepository.save(followed);
     }

     @Transactional
     public void unfollowUser(String followerUsername, String followedUsername) {
          User follower = getUserByUsername(followerUsername);
          User followed = getUserByUsername(followedUsername);

          // Remove from following set
          follower.getFollowing().remove(followed);
          // Remove from followers set
          followed.getFollowers().remove(follower);

          userRepository.save(follower);
          userRepository.save(followed);
     }

     @Transactional(readOnly = true)
     public List<User> getFollowers(String username) {
          User user = getUserByUsername(username);
          return user.getFollowers().stream().toList();
     }

     @Transactional(readOnly = true)
     public List<User> getFollowing(String username) {
          User user = getUserByUsername(username);
          return user.getFollowing().stream().toList();
     }

     @Transactional(readOnly = true)
     public boolean isFollowing(String followerUsername, String followedUsername) {
          User follower = getUserByUsername(followerUsername);
          User followed = getUserByUsername(followedUsername);
          return follower.getFollowing().contains(followed);
     }
}