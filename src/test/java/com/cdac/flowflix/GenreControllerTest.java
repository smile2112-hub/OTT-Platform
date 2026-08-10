/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.controller.GenreController;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

@ExtendWith(MockitoExtension.class)
class GenreControllerTest {

    @InjectMocks
    private GenreController controller;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnMoviesByGenre() {
        MovieDTO dto = mock(MovieDTO.class);
        when(movieService.getMoviesByGenre("Action")).thenReturn(List.of(dto));

        List<MovieDTO> result = controller.moviesByGenre("Action");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
