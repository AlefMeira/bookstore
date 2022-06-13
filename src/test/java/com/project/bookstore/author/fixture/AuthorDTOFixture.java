package com.project.bookstore.author.fixture;

import com.project.bookstore.author.dto.AuthorDTO;

public class AuthorDTOFixture {

    public static AuthorDTO authorDTO() {
        return AuthorDTO.builder()
                .id(1L)
                .name("Uncle Bob")
                .age(65)
                .build();
    }
}
