package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.repository.ReviewRepository;


@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;

    // ==========================
    // SAVE MOVIE
    // ==========================

    @Override
    public Movie save(Movie movie) {

        if (movie.getTotalViews() == null) {
            movie.setTotalViews(0L);
        }

        return movieRepository.save(movie);
    }

    // ==========================
    // FIND MOVIE
    // ==========================

    @Override
    public Movie findOne(Long id) {

        return movieRepository
                .findById(id)
                .orElse(null);

    }
    @Override
    public List<MovieDTO> getTopViewedMovies() {

        return movieRepository

                .findTop10ByOrderByTotalViewsDesc()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // FIND MOVIE DTO
    // ==========================

    @Override
    public MovieDTO findMovieDTOById(Long id) {

        Movie movie = findOne(id);

        if (movie == null) {
            return null;
        }

        return convertToDTO(movie);

    }

    // ==========================
    // DELETE ENTITY
    // ==========================

    @Override
    public void delete(Movie movie) {

        movieRepository.delete(movie);

    }

    // ==========================
    // GET ALL MOVIES
    // ==========================

    @Override
    public List<MovieDTO> findAll() {

        return movieRepository

                .findAll()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // FEATURED MOVIES
    // ==========================

    @Override
    public List<MovieDTO> getFeaturedMovies() {

        return movieRepository

                .findByFeaturedTrue()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // LATEST MOVIES
    // ==========================

    @Override
    public List<MovieDTO> getLatestMovies() {

        return movieRepository

                .findTop10ByOrderByIdDesc()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // TRENDING MOVIES
    // ==========================

    @Override
    public List<MovieDTO> getTrendingMovies() {

        return movieRepository

                .findByTrendingTrue()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // MOVIES BY GENRE
    // ==========================

    @Override
    public List<MovieDTO> getMoviesByGenre(String genre) {

        return movieRepository

                .findByGenreContainingIgnoreCase(genre)

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // SEARCH MOVIES
    // ==========================

    @Override
    public List<MovieDTO> searchMovies(String keyword) {

        return movieRepository

                .findByNameContainingIgnoreCase(keyword)

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // ACTIVE MOVIES
    // ==========================

    @Override
    public List<MovieDTO> getActiveMovies() {

        return movieRepository

                .findByActiveTrue()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // RECOMMEND MOVIES
    // ==========================

    @Override
    public List<MovieDTO> recommendMovies(Long movieId) {

        Movie movie = findOne(movieId);

        if (movie == null
                || movie.getGenre() == null
                || movie.getGenre().trim().isEmpty()) {

            return List.of();

        }

        return movieRepository

                .findByGenreContainingIgnoreCase(movie.getGenre())

                .stream()

                .filter(m -> m.getId() != null
                        && !m.getId().equals(movieId))

                .limit(10)

                .map(this::convertToDTO)

                .toList();

    }
    
    // ==========================
    // ACTIVATE MOVIE
    // ==========================

    @Override
    public void activateMovie(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setActive(true);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // DEACTIVATE MOVIE
    // ==========================

    @Override
    public void deactivateMovie(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setActive(false);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // FEATURE MOVIE
    // ==========================

    @Override
    public void featureMovie(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setFeatured(true);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // UNFEATURE MOVIE
    // ==========================

    @Override
    public void unFeatureMovie(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setFeatured(false);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // UPDATE MOVIE
    // ==========================

    @Override
    public Movie updateMovie(
            Long id,
            Movie updatedMovie) {

        Movie movie = findOne(id);

        if (movie == null) {

            return null;

        }

        movie.setName(updatedMovie.getName());

        movie.setDirector(updatedMovie.getDirector());

        movie.setDescription(updatedMovie.getDescription());

        movie.setGenre(updatedMovie.getGenre());

        movie.setActors(updatedMovie.getActors());

        movie.setDistributor(updatedMovie.getDistributor());

        movie.setYear(updatedMovie.getYear());

        movie.setDuration(updatedMovie.getDuration());

        movie.setRating(updatedMovie.getRating());

        if (updatedMovie.getPoster() != null) {

            movie.setPoster(updatedMovie.getPoster());

        }

        if (updatedMovie.getBanner() != null) {

            movie.setBanner(updatedMovie.getBanner());

        }

        if (updatedMovie.getTrailer() != null) {

            movie.setTrailer(updatedMovie.getTrailer());

        }

        if (updatedMovie.getVideo() != null) {

            movie.setVideo(updatedMovie.getVideo());

        }

        if (updatedMovie.getVideoSize() != null) {

            movie.setVideoSize(updatedMovie.getVideoSize());

        }

        movie.setFeatured(updatedMovie.isFeatured());

        movie.setTrending(updatedMovie.isTrending());

        movie.setActive(updatedMovie.isActive());

        return movieRepository.save(movie);

    }

    // ==========================
    // DELETE MOVIE
    // ==========================

    @Override
    public String deleteMovie(Long id) {

        Movie movie = findOne(id);

        if (movie == null) {

            return "Movie Not Found";

        }

        movieRepository.delete(movie);

        return "Movie Deleted Successfully";

    }

    // ==========================
    // MAKE TRENDING
    // ==========================

    @Override
    public void makeTrending(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setTrending(true);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // REMOVE TRENDING
    // ==========================

    @Override
    public void removeTrending(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            movie.setTrending(false);

            movieRepository.save(movie);

        }

    }
    
    // ==========================
    // INCREMENT MOVIE VIEWS
    // ==========================

    @Override
    public void incrementViews(Long id) {

        Movie movie = findOne(id);

        if (movie != null) {

            if (movie.getTotalViews() == null) {

                movie.setTotalViews(0L);

            }

            movie.setTotalViews(movie.getTotalViews() + 1);

            movieRepository.save(movie);

        }

    }

    // ==========================
    // MOST VIEWED MOVIES
    // ==========================

    @Override
    public List<MovieDTO> getMostViewedMovies() {

        return movieRepository

                .findTop10ByOrderByTotalViewsDesc()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    // ==========================
    // TOTAL VIEWS
    // ==========================

    @Override
    public Long getTotalViews() {

        Long totalViews = 0L;

        List<Movie> movies = movieRepository.findAll();

        for (Movie movie : movies) {

            if (movie.getTotalViews() != null) {

                totalViews += movie.getTotalViews();

            }

        }

        return totalViews;

    }

    // ==========================
    // MOST VIEWED MOVIE
    // ==========================

    @Override
    public String getMostViewedMovie() {

        Movie movie = movieRepository.findTopByOrderByTotalViewsDesc();

        if (movie == null) {

            return "No Movie";

        }

        return movie.getName();

    }

    // ==========================
    // TOP VIEWED MOVIES
    // ==========================

   

    // ==========================
    // CONVERT ENTITY TO DTO
    // ==========================

    private MovieDTO convertToDTO(Movie movie) {

        return new MovieDTO(movie);

    }


//======================================
//GET MOVIE AVERAGE RATING
//======================================

@Override
public Double getAverageRating(Long movieId) {

 Movie movie =
         findOne(movieId);

 if (movie == null) {

     return 0.0;

 }

 List<Review> reviews =
         reviewRepository.findByMovieOrderByReviewDateDesc(movie);

 if (reviews.isEmpty()) {

     return 0.0;

 }

 double total = 0;

 for (Review review : reviews) {

     total += review.getRating();

 }

 return total / reviews.size();

}

//======================================
//GET REVIEW COUNT
//======================================

@Override
public Long getReviewCount(Long movieId) {

 Movie movie =
         findOne(movieId);

 if (movie == null) {

     return 0L;

 }

 return reviewRepository.countByMovie(movie);

}


//======================================
//UPDATE MOVIE RATING
//======================================

@Override
public void updateMovieRating(Long movieId) {

 Movie movie =
         findOne(movieId);

 if (movie == null) {

     return;

 }

 Double avg =
         getAverageRating(movieId);

 if (avg == null) {

     avg = 0.0;

 }

 movie.setRating(avg);

 movieRepository.save(movie);

}
}