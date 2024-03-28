package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.entities.PostEntity;
import org.example.repositories.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    //final - як readlonly на С#
    private final PostRepository PostRepository;

    //HttpGet - аналог ASP.NET - отримання інформації
    @GetMapping
    public ResponseEntity<List<PostEntity>> index() {
        List<PostEntity> list = PostRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}