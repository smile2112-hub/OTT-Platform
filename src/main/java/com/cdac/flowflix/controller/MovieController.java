package com.cdac.flowflix.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.HistoryRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.repository.WatchHistoryRepository;
import com.cdac.flowflix.repository.WatchlistRepository;
import com.cdac.flowflix.repository.WatchProgressRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.storage.StorageService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private WatchProgressRepository watchProgressRepository;

    @Autowired
    private HistoryRepository historyRepository;


    // =========================================================
    // CREATE MOVIE
    // =========================================================

    @PostMapping(
            value = "/createMovie",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> createMovie(

            @RequestParam("poster")
            MultipartFile poster,

            @RequestParam(
                    value = "banner",
                    required = false
            )
            MultipartFile banner,

            @RequestParam("video")
            MultipartFile video,

            @RequestParam(
                    value = "trailer",
                    required = false
            )
            MultipartFile trailer,

            @RequestParam("movie")
            String movieJson

    ) throws IOException {


        // Check poster

        if (poster == null ||
                poster.isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Poster is required");
        }


        // Check video

        if (video == null ||
                video.isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Video is required");
        }


        // Convert JSON to Movie object

        Gson gson = new Gson();

        Movie movie =
                gson.fromJson(
                        movieJson,
                        Movie.class
                );


        if (movie == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid movie data");
        }


        // =====================================================
        // SAVE POSTER
        // =====================================================

        String posterName =
                storageService.savePoster(
                        poster
                );

        movie.setPoster(
                posterName
        );


        // =====================================================
        // SAVE BANNER
        // =====================================================

        if (banner != null &&
                !banner.isEmpty()) {

            String bannerName =
                    storageService.saveBanner(
                            banner
                    );

            movie.setBanner(
                    bannerName
            );
        }


        // =====================================================
        // SAVE VIDEO
        // =====================================================

        String videoName =
                storageService.saveVideo(
                        video
                );

        movie.setVideo(
                videoName
        );


        // =====================================================
        // SAVE TRAILER
        // =====================================================

        if (trailer != null &&
                !trailer.isEmpty()) {

            String trailerName =
                    storageService.saveTrailer(
                            trailer
                    );

            movie.setTrailer(
                    trailerName
            );
        }


        // =====================================================
        // VIDEO SIZE
        // =====================================================

        double sizeMB =
                video.getSize()
                        / (1024.0 * 1024.0);

        movie.setVideoSize(
                String.format(
                        "%.2f MB",
                        sizeMB
                )
        );


        // =====================================================
        // DEFAULT VALUES
        // =====================================================

        if (movie.getTotalViews() == null) {

            movie.setTotalViews(0L);
        }


        if (movie.getRating() == null) {

            movie.setRating(0.0);
        }


        movieService.save(
                movie
        );


        return ResponseEntity
                .ok(
                        "Movie Uploaded Successfully"
                );
    }


    // =========================================================
    // GET ALL MOVIES
    // =========================================================

    @GetMapping("/getAllMovies")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {

        return ResponseEntity.ok(
                movieService.findAll()
        );
    }


    // =========================================================
    // GET MOVIE BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(
            @PathVariable Long id) {

        MovieDTO movie =
                movieService.findMovieDTOById(
                        id
                );


        if (movie == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity.ok(
                movie
        );
    }


    // =========================================================
    // DELETE MOVIE
    // =========================================================

    @DeleteMapping("/deleteMovie/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<String> deleteMovie(
            @PathVariable Long id) {

        Movie movie =
                movieService.findOne(id);

        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        // =====================================================
        // DELETE ALL RELATED RECORDS FIRST
        // =====================================================

        favoriteRepository.deleteByMovie(movie);
        watchlistRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        watchHistoryRepository.deleteByMovie(movie);
        watchProgressRepository.deleteByMovie(movie);
        historyRepository.deleteByMovie(movie);

        // =====================================================
        // DELETE FILES
        // =====================================================

        if (movie.getPoster() != null) {
            try { storageService.deletePoster(movie.getPoster()); }
            catch (Exception e) { System.out.println("Poster delete error: " + e.getMessage()); }
        }

        if (movie.getBanner() != null) {
            try { storageService.deleteBanner(movie.getBanner()); }
            catch (Exception e) { System.out.println("Banner delete error: " + e.getMessage()); }
        }

        if (movie.getVideo() != null) {
            try { storageService.deleteVideo(movie.getVideo()); }
            catch (Exception e) { System.out.println("Video delete error: " + e.getMessage()); }
        }

        if (movie.getTrailer() != null) {
            try { storageService.deleteTrailer(movie.getTrailer()); }
            catch (Exception e) { System.out.println("Trailer delete error: " + e.getMessage()); }
        }

        // =====================================================
        // DELETE MOVIE
        // =====================================================

        movieService.delete(movie);

        return ResponseEntity.ok("Movie Deleted Successfully");
    }


    // =========================================================
    // FEATURED MOVIES
    // =========================================================

    @GetMapping("/featured")
    public ResponseEntity<List<MovieDTO>>
            featuredMovies() {

        return ResponseEntity.ok(
                movieService.getFeaturedMovies()
        );
    }


    // =========================================================
    // LATEST MOVIES
    // =========================================================

    @GetMapping("/latest")
    public ResponseEntity<List<MovieDTO>>
            latestMovies() {

        return ResponseEntity.ok(
                movieService.getLatestMovies()
        );
    }


    // =========================================================
    // TRENDING MOVIES
    // =========================================================

    @GetMapping("/trending")
    public ResponseEntity<List<MovieDTO>>
            trendingMovies() {

        return ResponseEntity.ok(
                movieService.getTrendingMovies()
        );
    }


    // =========================================================
    // MOVIES BY GENRE
    // =========================================================

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDTO>>
            genreMovies(
                    @PathVariable String genre) {

        return ResponseEntity.ok(
                movieService.getMoviesByGenre(
                        genre
                )
        );
    }


    // =========================================================
    // SEARCH MOVIES
    // =========================================================

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDTO>>
            searchMovies(
                    @PathVariable String keyword) {

        return ResponseEntity.ok(
                movieService.searchMovies(
                        keyword
                )
        );
    }


    // =========================================================
    // ACTIVE MOVIES
    // =========================================================

    @GetMapping("/active")
    public ResponseEntity<List<MovieDTO>>
            activeMovies() {

        return ResponseEntity.ok(
                movieService.getActiveMovies()
        );
    }


    // =========================================================
    // RECOMMEND MOVIES
    // =========================================================

    @GetMapping("/recommend/{movieId}")
    public ResponseEntity<List<MovieDTO>>
            recommendMovies(
                    @PathVariable Long movieId) {

        return ResponseEntity.ok(
                movieService.recommendMovies(
                        movieId
                )
        );
    }


    // =========================================================
    // ACTIVATE MOVIE
    // =========================================================

    @PutMapping("/activate/{id}")
    public ResponseEntity<String>
            activateMovie(
                    @PathVariable Long id) {

        if (movieService.findOne(id) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        movieService.activateMovie(id);


        return ResponseEntity.ok(
                "Movie Activated"
        );
    }


    // =========================================================
    // DEACTIVATE MOVIE
    // =========================================================

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String>
            deactivateMovie(
                    @PathVariable Long id) {

        if (movieService.findOne(id) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        movieService.deactivateMovie(id);


        return ResponseEntity.ok(
                "Movie Deactivated"
        );
    }


    // =========================================================
    // FEATURE MOVIE
    // =========================================================

    @PutMapping("/feature/{id}")
    public ResponseEntity<String>
            featureMovie(
                    @PathVariable Long id) {

        if (movieService.findOne(id) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        movieService.featureMovie(id);


        return ResponseEntity.ok(
                "Movie Featured"
        );
    }


    // =========================================================
    // UNFEATURE MOVIE
    // =========================================================

    @PutMapping("/unfeature/{id}")
    public ResponseEntity<String>
            unFeatureMovie(
                    @PathVariable Long id) {

        if (movieService.findOne(id) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        movieService.unFeatureMovie(id);


        return ResponseEntity.ok(
                "Movie Unfeatured"
        );
    }


    // =========================================================
    // SERVE POSTER
    // =========================================================

    @GetMapping("/poster/{filename:.+}")
    public ResponseEntity<Resource> getPoster(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getPoster(
                        filename
                );


        if (data == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        ByteArrayResource resource =
                new ByteArrayResource(data);


        return ResponseEntity.ok()
                .contentType(
                        getImageMediaType(filename)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                filename +
                                "\""
                )
                .body(resource);
    }


    // =========================================================
    // SERVE BANNER
    // =========================================================

    @GetMapping("/banner/{filename:.+}")
    public ResponseEntity<Resource> getBanner(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getBanner(
                        filename
                );


        if (data == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        ByteArrayResource resource =
                new ByteArrayResource(data);


        return ResponseEntity.ok()
                .contentType(
                        getImageMediaType(filename)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                filename +
                                "\""
                )
                .body(resource);
    }


    // =========================================================
    // SERVE VIDEO
    // =========================================================

    @GetMapping("/video/{filename:.+}")
    public ResponseEntity<Resource> getVideo(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getVideo(
                        filename
                );


        if (data == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        ByteArrayResource resource =
                new ByteArrayResource(data);


        return ResponseEntity.ok()
                .contentType(
                        getVideoMediaType(filename)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                filename +
                                "\""
                )
                .body(resource);
    }


    // =========================================================
    // SERVE TRAILER
    // =========================================================

    @GetMapping("/trailer/{filename:.+}")
    public ResponseEntity<Resource> getTrailer(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getTrailer(
                        filename
                );


        if (data == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        ByteArrayResource resource =
                new ByteArrayResource(data);


        return ResponseEntity.ok()
                .contentType(
                        getVideoMediaType(filename)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                filename +
                                "\""
                )
                .body(resource);
    }


    // =========================================================
    // IMAGE MEDIA TYPE
    // =========================================================

    private MediaType getImageMediaType(
            String filename) {

        String name =
                filename.toLowerCase();


        if (name.endsWith(".png")) {

            return MediaType.IMAGE_PNG;
        }


        if (name.endsWith(".gif")) {

            return MediaType.IMAGE_GIF;
        }


        if (name.endsWith(".webp")) {

            return MediaType.parseMediaType(
                    "image/webp"
            );
        }


        return MediaType.IMAGE_JPEG;
    }


    // =========================================================
    // VIDEO MEDIA TYPE
    // =========================================================

    private MediaType getVideoMediaType(
            String filename) {

        String name =
                filename.toLowerCase();


        if (name.endsWith(".webm")) {

            return MediaType.parseMediaType(
                    "video/webm"
            );
        }


        if (name.endsWith(".ogg")) {

            return MediaType.parseMediaType(
                    "video/ogg"
            );
        }


        return MediaType.parseMediaType(
                "video/mp4"
        );
    }
    
    
    @PutMapping("/view/{id}")
    public ResponseEntity<String> viewMovie(
            @PathVariable Long id) {

        movieService.incrementViews(id);

        return ResponseEntity.ok(
                "View Count Updated");

    }
    
}