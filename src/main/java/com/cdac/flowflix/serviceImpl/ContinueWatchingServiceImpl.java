/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.ContinueWatchingService;

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import com.cdac.flowflix.service.WatchProgressService;

@Service
public class ContinueWatchingServiceImpl extends ContinueWatchingService {

    @Autowired
    private WatchProgressService watchProgressService;

    public List<WatchProgressDTO> getContinueWatching() {

        return watchProgressService.continueWatching();

    }

}
