package com.project.bookstore.author.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.author.dto.AuthorDTO;
import com.project.bookstore.author.fixture.AuthorDTOFixture;
import com.project.bookstore.author.service.AuthorService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test for author controller
 */

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebMvc
@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {

    public static final String AUTHOR_URI = "/api/v1/authors";
    @MockBean
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test with success then created a new author with success")
    void whenPOSTIsCalledWhenStatusCreatedShouldBeReturned() throws Exception {
        //GIVEN
        final var expectedCreateAuthorDTO = AuthorDTOFixture.authorDTO();

        //WHEN
        when(this.authorService.create(expectedCreateAuthorDTO))
                .thenReturn(expectedCreateAuthorDTO);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTHOR_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.asJsonString(expectedCreateAuthorDTO));

        //THEN
        this.mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedCreateAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedCreateAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedCreateAuthorDTO.getAge())));

        verify(this.authorService).create(expectedCreateAuthorDTO);
    }


    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestShouldBeInformed() throws Exception {
        //GIVEN
        final var expectedCreateAuthorDTO = AuthorDTOFixture.authorDTO();
        expectedCreateAuthorDTO.setName(null);
        //WHEN
        when(this.authorService.create(expectedCreateAuthorDTO)).thenReturn(expectedCreateAuthorDTO);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTHOR_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.asJsonString(expectedCreateAuthorDTO));

        //THEN
        this.mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(this.authorService, never()).create(any());
    }

    private String asJsonString(final AuthorDTO expectedCreateAuthorDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(expectedCreateAuthorDTO);
    }
}
