package com.cdac.flowflix.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.storage.StorageService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin("*")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private StorageService storageService;

    @PostMapping(value="/createMovie",
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createMovie(

            @RequestParam("poster") MultipartFile poster,

            @RequestParam(value="banner",required=false)
            MultipartFile banner,

            @RequestParam("video")
            MultipartFile video,

            @RequestParam(value="trailer",required=false)
            MultipartFile trailer,

            @RequestParam("movie")
            String movieJson) throws IOException {

        Gson gson=new Gson();

        Movie movie=gson.fromJson(movieJson,Movie.class);

        movie.setPoster(storageService.savePoster(poster));

        if(banner!=null)
            movie.setBanner(storageService.saveBanner(banner));

        movie.setVideo(storageService.saveVideo(video));

        if(trailer!=null)
            movie.setTrailer(storageService.saveVideo(trailer));

        movie.setVideoSize((video.getSize()/1024/1024)+" MB");

        movieService.save(movie);

        return ResponseEntity.ok("Movie Uploaded Successfully");
    }

    @GetMapping("/getAllMovies")
    public List<MovieDTO> getAllMovies(){
        return movieService.findAll();
    }

    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){

        Movie movie=movieService.findOne(id);

        if(movie==null){
            return ResponseEntity.ok("Movie Not Found");
        }

        movieService.delete(movie);

        return ResponseEntity.ok("Deleted Successfully");
    }

    @GetMapping("/featured")
    public List<MovieDTO> featuredMovies(){
        return movieService.getFeaturedMovies();
    }

    @GetMapping("/latest")
    public List<MovieDTO> latestMovies(){
        return movieService.getLatestMovies();
    }

    @GetMapping("/trending")
    public List<MovieDTO> trendingMovies(){
        return movieService.getTrendingMovies();
    }

    @GetMapping("/genre/{genre}")
    public List<MovieDTO> genreMovies(@PathVariable String genre){
        return movieService.getMoviesByGenre(genre);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateMovie(@PathVariable Long id){

        if(movieService.findOne(id)==null)
            return ResponseEntity.ok("Movie Not Found");

        movieService.activateMovie(id);

        return ResponseEntity.ok("Movie Activated");
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateMovie(@PathVariable Long id){

        if(movieService.findOne(id)==null)
            return ResponseEntity.ok("Movie Not Found");

        movieService.deactivateMovie(id);

        return ResponseEntity.ok("Movie Deactivated");
    }

    @PutMapping("/feature/{id}")
    public ResponseEntity<String> featureMovie(@PathVariable Long id){

        if(movieService.findOne(id)==null)
            return ResponseEntity.ok("Movie Not Found");

        movieService.featureMovie(id);

        return ResponseEntity.ok("Movie Featured");
    }

    @PutMapping("/unfeature/{id}")
    public ResponseEntity<String> unFeatureMovie(@PathVariable Long id){

        if(movieService.findOne(id)==null)
            return ResponseEntity.ok("Movie Not Found");

        movieService.unFeatureMovie(id);

        return ResponseEntity.ok("Movie Unfeatured");
    }
    @GetMapping("/search/{keyword}")
    public List<MovieDTO> searchMovies(@PathVariable String keyword){

        return movieService.searchMovies(keyword);

    }

    @GetMapping("/active")
    public List<MovieDTO> activeMovies(){

        return movieService.getActiveMovies();

    }
    
    @GetMapping("/recommend/{movieId}")
    public List<MovieDTO> recommendMovies(
            @PathVariable Long movieId){

        return movieService.recommendMovies(movieId);

    }

}