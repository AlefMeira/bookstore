package com.project.bookstore.author.converter;

import com.project.bookstore.author.dto.AuthorDTO;
import com.project.bookstore.author.entity.Author;

public class AuthorConverter {

    private AuthorConverter() {
        throw new IllegalStateException("utility class");
    }

    public static Author authorToAuthorDTO(final AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .age(authorDTO.getAge())
                .build();
    }

    public static AuthorDTO authorDTOToAuthor(final Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .age(author.getAge())
                .build();
    }
}
