package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchProgress;
import com.cdac.flowflix.repository.WatchProgressRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.service.WatchProgressService;

@Service
@Transactional
public class WatchProgressServiceImpl implements WatchProgressService{

    @Autowired
    private WatchProgressRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Override
    public void saveProgress(Long movieId,
                             Long watchedSeconds,
                             Long totalSeconds){

        User user=userService.getCurrentUser();

        Movie movie=movieService.findOne(movieId);

        if(user==null || movie==null)
            return;

        WatchProgress progress=
                repository.findByUserAndMovie(user,movie);

        if(progress==null){

            progress=new WatchProgress();

            progress.setUser(user);

            progress.setMovie(movie);

        }

        progress.setWatchedSeconds(watchedSeconds);

        progress.setTotalSeconds(totalSeconds);

        progress.setProgress(
                (watchedSeconds*100.0)/totalSeconds);

        progress.setLastWatched(System.currentTimeMillis());

        if(progress.getProgress()>95)
            progress.setCompleted(true);

        repository.save(progress);

    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchProgressDTO> continueWatching(){

        User user=userService.getCurrentUser();

        List<WatchProgressDTO> list=new ArrayList<>();

        for(WatchProgress progress:
            repository.findByUserOrderByLastWatchedDesc(user)){

            if(!progress.isCompleted())
                list.add(new WatchProgressDTO(progress));

        }

        return list;

    }
    @Override
    public Long getResumeTime(Long movieId) {

        User user = userService.getCurrentUser();

        Movie movie = movieService.findOne(movieId);

        if (user == null || movie == null) {

            return 0L;

        }

        WatchProgress progress =
                repository.findByUserAndMovie(user, movie);

        if (progress == null) {

            return 0L;

        }

        return progress.getWatchedSeconds();

    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchProgressDTO> watchHistory(){

        User user=userService.getCurrentUser();

        List<WatchProgressDTO> list=new ArrayList<>();

        for(WatchProgress progress:
            repository.findByUserOrderByLastWatchedDesc(user)){

            list.add(new WatchProgressDTO(progress));

        }

        return list;

    }

}