package com.project.bookstore.author.service;

import com.project.bookstore.author.converter.AuthorConverter;
import com.project.bookstore.author.dto.AuthorDTO;
import com.project.bookstore.author.exception.AuthorAlreadyExistException;
import com.project.bookstore.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.bookstore.author.converter.AuthorConverter.authorDTOToAuthor;
import static com.project.bookstore.author.converter.AuthorConverter.authorToAuthorDTO;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorDTO create(final AuthorDTO authorDTO) throws AuthorAlreadyExistException {
        this.verifyIfExistsAuthorByName(authorDTO.getName());
        final var authorForCreate = authorDTOToAuthor(authorDTO);
        final var createdAuthor = this.authorRepository.save(authorForCreate);
        return authorToAuthorDTO(createdAuthor);
    }

    public List<AuthorDTO> list() {
        return this.authorRepository.findAll()
                .stream()
                .map(AuthorConverter::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    private void verifyIfExistsAuthorByName(final String authorName) throws AuthorAlreadyExistException {
        final var author = this.authorRepository.findByName(authorName);

        if (author.isPresent()) {
            throw new AuthorAlreadyExistException(authorName);
        }
    }
}
