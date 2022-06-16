package com.project.bookstore.author.controller;

import com.project.bookstore.author.dto.AuthorDTO;
import com.project.bookstore.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements AuthorControllerDocs {

    @Autowired
    private AuthorService authorService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid final AuthorDTO authorDTO) {
        return this.authorService.create(authorDTO);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDTO> list() {
        return this.authorService.list();
    }
}
