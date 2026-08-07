package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.FavoriteController;
import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.service.FavoriteService;

@ExtendWith(MockitoExtension.class)
class FavoriteControllerTest {

    @InjectMocks
    private FavoriteController controller;

    @Mock
    private FavoriteService favoriteService;

    @Test
    void shouldAddFavorite() {
        when(favoriteService.addFavorite(10L)).thenReturn("Favorite added");

        ResponseEntity<String> response = controller.addFavorite(10L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Favorite added", response.getBody());
        verify(favoriteService).addFavorite(10L);
    }

    @Test
    void shouldReturnMyFavorites() {
        FavoriteDTO dto = mock(FavoriteDTO.class);
        when(favoriteService.getMyFavorites()).thenReturn(List.of(dto));

        List<FavoriteDTO> response = controller.myFavorites();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertSame(dto, response.get(0));
    }

    @Test
    void shouldCheckFavoriteAndCountValues() {
        when(favoriteService.isFavorite(5L)).thenReturn(true);
        when(favoriteService.getTotalFavorites()).thenReturn(50L);
        when(favoriteService.getUserFavoriteCount()).thenReturn(5L);
        when(favoriteService.getMovieFavoriteCount(5L)).thenReturn(3L);

        assertTrue(controller.checkFavorite(5L).getBody());
        assertEquals(50L, controller.totalFavorites().getBody());
        assertEquals(5L, controller.myFavoriteCount().getBody());
        assertEquals(3L, controller.movieFavoriteCount(5L).getBody());
    }
}
