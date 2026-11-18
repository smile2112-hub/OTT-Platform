import React, { useEffect, useState } from "react";
import {
    Alert,
    Badge,
    Card,
    Container,
    Spinner,
    Table
} from "react-bootstrap";
import ReviewService from "../services/ReviewService";

const MyReviews = () => {

    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadReviews = async () => {
        try {
            setLoading(true);
            const response = await ReviewService.getMyReviews();
            setReviews(response.data);
        }
        catch (err) {
            console.log(err);
            setError("Unable to load your reviews.");
        }
        finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadReviews();
    }, []);

    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" variant="danger" />
            </Container>
        );
    }



    return (
        <Container className="mt-4">
            <Card className="shadow">
                <Card.Header className="bg-dark text-white">
                    <h3 className="mb-0">My Reviews</h3>
                </Card.Header>
                <Card.Body>
                    {error && <Alert variant="danger">{error}</Alert>}

                    {reviews.length === 0 ? (
                        <div className="text-center py-4">
                            No reviews found.
                        </div>
                    ) : (
                        <Table responsive bordered hover>
                            <thead className="table-dark">
                                <tr>
                                    <th>Movie</th>
                                    <th>Rating</th>
                                    <th>Review</th>
                                    <th>Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                {reviews.map((review) => (
                                    <tr key={review.reviewId}>
                                        <td>{review.movieName || "-"}</td>
                                        <td>
                                            <Badge bg="warning" text="dark">
                                                ⭐ {review.rating}
                                            </Badge>
                                        </td>
                                        <td>{review.review || "-"}</td>
                                        <td>{review.reviewDate ? new Date(review.reviewDate).toLocaleString() : "-"}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                    )}
                </Card.Body>
            </Card>
        </Container>
    );
};

export default MyReviews;
