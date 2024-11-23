// With validation
package com.proj.streetfeed.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class UserDTO {
     @NotBlank(message = "Username cannot be blank")
     @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
     private String username;

     @NotBlank(message = "Password cannot be blank")
     @Size(min = 6, message = "Password must be at least 6 characters long")
     private String password;

     @NotBlank(message = "Email cannot be blank")
     @Email(message = "Invalid email format")
     private String email;

     private String profilePicture; // Optional
}