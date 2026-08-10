/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import com.cdac.flowflix.controller.SearchController;

class SearchControllerTest {

    @Test
    void shouldInstantiateSearchController() {
        SearchController controller = new SearchController();

        assertNotNull(controller);
    }
}
