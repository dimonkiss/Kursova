package org.example.dto;

import lombok.Data;

@Data
public class PostItemDTO {
    private int id;
    private String title;
    private String image;
    private String content;
    private String dateCreated;
}