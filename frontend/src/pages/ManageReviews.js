/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState } from "react";
import ReviewService from "../services/ReviewService";

const ManageReviews = () => {

    const [reviews, setReviews] = useState([]);

    useEffect(() => {

        loadReviews();

    }, []);

    const loadReviews = async () => {

        try {

            const response =
                await ReviewService.getAllReviews();

            setReviews(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const deleteReview = async (id) => {

        if (!window.confirm("Delete this review?")) {

            return;

        }

        try {

            await ReviewService.deleteReview(id);

            loadReviews();

        }

        catch (error) {

            alert("Unable to delete review");

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">

                Manage Reviews

            </h2>

            <div className="table-responsive">

                <table className="table table-bordered table-hover">

                    <thead className="table-dark">

                        <tr>

                            <th>ID</th>

                            <th>User</th>

                            <th>Movie</th>

                            <th>Rating</th>

                            <th>Comment</th>

                            <th>Date</th>

                            <th>Action</th>

                        </tr>

                    </thead>

                    <tbody>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                        {

                            reviews.length === 0 ?

                            <tr>

                                <td
                                    colSpan="7"
                                    className="text-center"
                                >

                                    No Reviews Found

                                </td>

                            </tr>

                            :

                            reviews.map(review => (

                                <tr key={review.id}>

                                    <td>

                                        {review.id}

                                    </td>

                                    <td>

                                        {review.userName}

                                    </td>

                                    <td>

                                        {review.movieName}

                                    </td>

                                    <td>

                                        ‚≠ê {review.rating}

                                    </td>

                                    <td>

                                        {review.comment}

                                    </td>

                                    <td>

                                        {review.reviewDate}

                                    </td>

                                    <td>

                                        <button

                                            className="btn btn-danger btn-sm"

                                            onClick={() =>

                                                deleteReview(review.id)

                                            }

                                        >

                                            Delete

                                        </button>

                                    </td>

                                </tr>

                            ))

                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

};

export default ManageReviews;
