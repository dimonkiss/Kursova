package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.PostCreateDTO;
import org.example.dto.PostEditDTO;
import org.example.dto.PostItemDTO;
import org.example.entities.PostEntity;
import org.example.mapper.PostMapper;
import org.example.repositories.PostRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.ScatteringByteChannel;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    //final - як readlonly на С#
    private final PostRepository PostRepository;
    private final PostMapper PostMapper;
    private final StorageService storageService;

    //HttpGet - аналог ASP.NET - отримання інформації
    @GetMapping
    public ResponseEntity<List<PostItemDTO>> index() {
        var model = PostMapper.postsListItemDTO(PostRepository.findAll());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostItemDTO> create(@ModelAttribute PostCreateDTO dto) {
        try {
            var entity = PostMapper.PostEntityByPostCreateDTO(dto);
            entity.setDatePosted(LocalDateTime.now());
            String fileName = storageService.SaveImage(dto.getFile(), FileSaveFormat.WEBP);
            entity.setImageUrl(fileName);
            PostRepository.save(entity);
            var result = PostMapper.PostItemDTO(entity);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostItemDTO> edit(@ModelAttribute PostEditDTO model) {
        var old = PostRepository.findById(model.getId()).orElse(null);
        if (old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var entity = PostMapper.PostEditDto(model);
        if(model.getFile()==null) {
            entity.setImageUrl(old.getImageUrl());
        }
        else {
            try {
                storageService.deleteImage(old.getImageUrl());
                String fileName = storageService.SaveImage(model.getFile(), FileSaveFormat.WEBP);
                entity.setImageUrl(fileName);
            }
            catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        PostRepository.save(entity);
        var result = PostMapper.PostItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Method to delete a post by ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable int postId) {
        var entity = PostRepository.findById(postId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            storageService.deleteImage(entity.getImageUrl());
            PostRepository.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostItemDTO> getById(@PathVariable int postId) {
        var entity = PostRepository.findById(postId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result =  PostMapper.PostItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}