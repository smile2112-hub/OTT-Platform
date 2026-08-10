/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import API from "./axiosConfig";

const FavoriteService = {

    addFavorite: function (
        movieId
    ) {

        return API.post(
            `/favorite/add/${movieId}`
        );

    },


    getFavorites: function () {

        return API.get(
            "/favorite/my"
        );


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

    },


    isFavorite: function (
        movieId
    ) {

        return API.get(
            `/favorite/check/${movieId}`
        );

    },


    removeFavorite: function (
        movieId
    ) {

        return API.delete(
            `/favorite/remove/${movieId}`
        );

    }

};

export default FavoriteService;
