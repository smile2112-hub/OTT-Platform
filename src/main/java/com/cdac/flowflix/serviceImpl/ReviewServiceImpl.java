package com.cdac.flowflix.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.service.ReviewService;
import com.cdac.flowflix.service.UserService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    // ==========================
    // ADD REVIEW
    // ==========================

    @Override
    public String addReview(
            Long movieId,
            Integer rating,
            String reviewText) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        if (reviewRepository.existsByUserAndMovie(user, movie)) {

            return "You Have Already Reviewed This Movie";

        }

        if (rating == null || rating < 1 || rating > 5) {

            return "Rating Must Be Between 1 And 5";

        }

        Review review = new Review();

        review.setUser(user);

        review.setMovie(movie);

        review.setRating(rating);

        review.setReview(reviewText);

        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);

        return "Review Added Successfully";

    }

    // ==========================
    // UPDATE REVIEW
    // ==========================

    @Override
    public String updateReview(
            Long movieId,
            Integer rating,
            String reviewText) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        Review review =
                reviewRepository.findByUserAndMovie(
                        user,
                        movie);

        if (review == null) {

            return "Review Not Found";

        }

        if (rating != null) {

            if (rating < 1 || rating > 5) {

                return "Rating Must Be Between 1 And 5";

            }

            review.setRating(rating);

        }

        review.setReview(reviewText);

        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);

        return "Review Updated Successfully";

    }

    // ==========================
    // DELETE REVIEW
    // ==========================

    @Override
    public String deleteReview(
            Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        Review review =
                reviewRepository.findByUserAndMovie(
                        user,
                        movie);

        if (review == null) {

            return "Review Not Found";

        }

        reviewRepository.delete(review);

        return "Review Deleted Successfully";

    }
    
    // ==========================
    // GET ALL REVIEWS OF MOVIE
    // ==========================

    @Override
    public List<ReviewDTO> getMovieReviews(
            Long movieId) {

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return List.of();

        }

        return reviewRepository

                .findByMovieOrderByReviewDateDesc(movie)

                .stream()

                .map(ReviewDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // GET MY REVIEW
    // ==========================

    @Override
    public ReviewDTO getMyReview(
            Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return null;

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return null;

        }

        Review review =
                reviewRepository.findByUserAndMovie(
                        user,
                        movie);

        if (review == null) {

            return null;

        }

        return new ReviewDTO(review);

    }

    // ==========================
    // HAS REVIEWED
    // ==========================

    @Override
    public boolean hasReviewed(
            Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return false;

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return false;

        }

        return reviewRepository.existsByUserAndMovie(
                user,
                movie);

    }

    // ==========================
    // GET AVERAGE RATING
    // ==========================

    @Override
    public Double getAverageRating(
            Long movieId) {

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

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

    // ==========================
    // GET REVIEW COUNT
    // ==========================

    @Override
    public Long getReviewCount(
            Long movieId) {

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return 0L;

        }

        return reviewRepository.countByMovie(movie);

    }

    @Override
    public List<ReviewDTO> getAllReviews() {

        return reviewRepository
                .findAll()
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<ReviewDTO> getMyReviews() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return reviewRepository
                .findByUserOrderByReviewDateDesc(user)
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {

        return reviewRepository
                .findById(reviewId)
                .map(ReviewDTO::new)
                .orElse(null);

    }

    @Override
    public String updateReviewById(
            Long reviewId,
            Integer rating,
            String reviewText) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Review review = reviewRepository
                .findById(reviewId)
                .orElse(null);

        if (review == null) {

            return "Review Not Found";

        }

        if (!review.getUser().getId().equals(user.getId()) &&
                user.getRole() != Role.ADMIN) {

            return "Unauthorized";

        }

        if (rating != null) {

            if (rating < 1 || rating > 5) {

                return "Rating Must Be Between 1 And 5";

            }

            review.setRating(rating);

        }

        review.setReview(reviewText);
        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);

        return "Review Updated Successfully";

    }

    @Override
    public String deleteReviewById(
            Long reviewId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Review review = reviewRepository
                .findById(reviewId)
                .orElse(null);

        if (review == null) {

            return "Review Not Found";

        }

        if (!review.getUser().getId().equals(user.getId()) &&
                user.getRole() != Role.ADMIN) {

            return "Unauthorized";

        }

        reviewRepository.delete(review);

        return "Review Deleted Successfully";

    }

}