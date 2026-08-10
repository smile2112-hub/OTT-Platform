/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.service;

import java.io.File;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

public interface VideoService {

    String getVideoPath(Long movieId);

	File getVideo(Long movieId);

	File getTrailer(Long movieId);

}
