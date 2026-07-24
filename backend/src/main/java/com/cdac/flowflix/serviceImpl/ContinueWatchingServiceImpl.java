package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.ContinueWatchingService;
import com.cdac.flowflix.service.WatchProgressService;

@Service
public class ContinueWatchingServiceImpl extends ContinueWatchingService {

    @Autowired
    private WatchProgressService watchProgressService;

    public List<WatchProgressDTO> getContinueWatching() {

        return watchProgressService.continueWatching();

    }

}