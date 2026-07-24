package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addReview(ReviewDTO dto) {

        if(dto.getRating()==null ||
                dto.getRating()<1 ||
                dto.getRating()>5){

            return "Invalid Rating";
        }

        Review review=new Review();

        review.setMovieId(dto.getMovieId());
        review.setUsername(dto.getUsername());
        review.setReview(dto.getReview());
        review.setRating(dto.getRating());

        reviewRepository.save(review);

        updateMovieRating(dto.getMovieId());

        return "Review Added Successfully";

    }

    @Override
    public List<Review> getMovieReviews(Long movieId) {

        return reviewRepository.findByMovieId(movieId);

    }

    @Override
    public List<Review> getUserReviews(String username) {

        return reviewRepository.findByUsername(username);

    }

    @Override
    public void deleteReview(Long id) {

        Review review=reviewRepository.findById(id).orElse(null);

        if(review!=null){

            Long movieId=review.getMovieId();

            reviewRepository.delete(review);

            updateMovieRating(movieId);

        }

    }

    private void updateMovieRating(Long movieId){

        Movie movie=movieRepository.findById(movieId).orElse(null);

        if(movie==null)
            return;

        List<Review> reviews=
                reviewRepository.findByMovieId(movieId);

        if(reviews.isEmpty()){

            movie.setRating(0.0);

        }else{

            double total=0;

            for(Review review:reviews){

                total+=review.getRating();

            }

            movie.setRating(total/reviews.size());

        }

        movieRepository.save(movie);

    }

}