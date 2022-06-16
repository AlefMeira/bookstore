package com.project.bookstore.author.controller;

import com.project.bookstore.author.dto.AuthorDTO;
import com.project.bookstore.author.exception.AuthorAlreadyExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Authors management")
public interface AuthorControllerDocs {

    @ApiOperation("Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success author creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong range value or author already " +
                    "registry in the system")
    })
    AuthorDTO create(final AuthorDTO authorDTO) throws AuthorAlreadyExistException;


    @ApiOperation("List all registered authors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned all registered authors")
    })
    List<AuthorDTO> list();

}
