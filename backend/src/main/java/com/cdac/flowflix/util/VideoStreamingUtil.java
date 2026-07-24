package com.cdac.flowflix.util;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletResponse;

public class VideoStreamingUtil {

    public static final int BUFFER_SIZE = 1024 * 1024;

    public static void streamVideo(
            RandomAccessFile file,
            HttpServletResponse response,
            long start,
            long end)
            throws IOException {

        byte[] buffer = new byte[BUFFER_SIZE];

        file.seek(start);

        long bytesLeft = end - start + 1;

        while (bytesLeft > 0) {

            int bytesToRead =
                    (int) Math.min(buffer.length, bytesLeft);

            int read = file.read(buffer, 0, bytesToRead);

            if (read == -1) {
                break;
            }

            response.getOutputStream()
                    .write(buffer, 0, read);

            bytesLeft -= read;

        }

    }

}