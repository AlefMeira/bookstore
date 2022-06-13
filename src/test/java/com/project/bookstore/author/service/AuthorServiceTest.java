package com.project.bookstore.author.service;

import com.project.bookstore.author.entity.Author;
import com.project.bookstore.author.exception.AuthorAlreadyExistException;
import com.project.bookstore.author.fixture.AuthorDTOFixture;
import com.project.bookstore.author.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.project.bookstore.author.converter.AuthorConverter.authorDTOToAuthor;
import static com.project.bookstore.author.fixture.AuthorDTOFixture.authorDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * Test for AuthorService class
 */
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("When new author is informed then it should be created")
    void whenNewAuthorIsInformedThenItShouldBeCreated() throws AuthorAlreadyExistException {
        //GIVEN
        final var expectedAuthorCreatedDTO = authorDTO();
        final var expectedCreatedAuthor = authorDTOToAuthor(expectedAuthorCreatedDTO);
        //WHEN
        when(this.authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(this.authorRepository.findByName(any())).thenReturn(Optional.empty());

        final var createdAuthorDTO = this.authorService.create(expectedAuthorCreatedDTO);
        //THEN
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorCreatedDTO)));
        verify(this.authorRepository).save(any());
    }

    @Test
    @DisplayName("When an existing author is informed then there should be an error returned")
    void whenExistsAuthorIsInformedThenAnExceptionShouldBeThrown() {
        //GIVEN
        final var expectedAuthorCreatedDTO = AuthorDTOFixture.authorDTO();
        //WHEN
        when(this.authorRepository.findByName(expectedAuthorCreatedDTO.getName()))
                .thenReturn(Optional.of(new Author()));
        //THEN
        assertThrows(AuthorAlreadyExistException.class,
                () -> this.authorService.create(expectedAuthorCreatedDTO));

        verify(this.authorRepository, never()).save(any());
    }
}
