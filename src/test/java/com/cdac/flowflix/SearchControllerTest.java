package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cdac.flowflix.controller.SearchController;

class SearchControllerTest {

    @Test
    void shouldInstantiateSearchController() {
        SearchController controller = new SearchController();

        assertNotNull(controller);
    }
}
