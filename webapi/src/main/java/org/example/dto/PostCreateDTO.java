package org.example.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDTO {
    private String title;
    private MultipartFile file;
    private String content;
}