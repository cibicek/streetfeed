// PostDTO.java
package com.proj.streetfeed.dto;

import lombok.Data;

@Data
public class PostDTO {
    private String caption;
    private Double latitude;
    private Double longitude;
    private Float heading;
    private Float pitch;
}