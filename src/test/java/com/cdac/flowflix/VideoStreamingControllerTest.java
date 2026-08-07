package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.cdac.flowflix.controller.VideoStreamingController;
import com.cdac.flowflix.service.VideoService;

@ExtendWith(MockitoExtension.class)
class VideoStreamingControllerTest {

    @InjectMocks
    private VideoStreamingController controller;

    @Mock
    private VideoService videoService;

    @Test
    void shouldReturnNotFoundWhenMovieFileMissing() throws IOException {
        when(videoService.getVideo(1L)).thenReturn(null);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.streamMovie(1L, request, response);

        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }

    @Test
    void shouldStreamMovieWithRange() throws IOException {
        File temp = File.createTempFile("test-video", ".mp4");
        try (FileOutputStream out = new FileOutputStream(temp)) {
            out.write(new byte[] {1, 2, 3, 4, 5});
        }

        when(videoService.getVideo(2L)).thenReturn(temp);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Range", "bytes=0-1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.streamMovie(2L, request, response);

        assertEquals(HttpServletResponse.SC_PARTIAL_CONTENT, response.getStatus());
        assertEquals("bytes", response.getHeader("Accept-Ranges"));
        assertEquals("bytes 0-1/5", response.getHeader("Content-Range"));
        assertEquals(2, response.getContentAsByteArray().length);
    }
}
