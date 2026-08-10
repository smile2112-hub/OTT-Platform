/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState } from "react";

import {
    Badge,
    Button,
    Card,
    Col,
    Container,
    Form,
    InputGroup,
    Modal,
    Pagination,
    Row,
    Spinner,
    Table
} from "react-bootstrap";

import {
    FaEye,
    FaSearch,
    FaTrash,
    FaStar
} from "react-icons/fa";

import ReviewService from "../../services/ReviewService";

const ManageReviews = () => {

    // =====================================
    // STATES
    // =====================================

    const [reviews, setReviews] = useState([]);

    const [filteredReviews, setFilteredReviews] = useState([]);

    const [loading, setLoading] = useState(true);

    const [search, setSearch] = useState("");

    const [ratingFilter, setRatingFilter] = useState("");

    const [selectedReview, setSelectedReview] = useState(null);

    const [showDetails, setShowDetails] = useState(false);

    const [showDelete, setShowDelete] = useState(false);

    const [reviewToDelete, setReviewToDelete] = useState(null);

    const [currentPage, setCurrentPage] = useState(1);

    const reviewsPerPage = 10;

    // =====================================
    // LOAD REVIEWS
    // =====================================

    useEffect(() => {

        loadReviews();

    }, []);

    const loadReviews = async () => {

        try {

            setLoading(true);

            const response =
                await ReviewService.getAllReviews();

            setReviews(response.data);

            setFilteredReviews(response.data);

        }

        catch (error) {

            console.log(error);

        }

        finally {

            setLoading(false);

        }

    };

    // =====================================
    // SEARCH + FILTER
    // =====================================

    useEffect(() => {

        let data = [...reviews];

        if (search.trim() !== "") {

            const keyword = search.toLowerCase();

            data = data.filter(review =>

                (review.user?.username || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (review.movie?.name || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (review.comment || "")
                    .toLowerCase()
                    .includes(keyword)

            );

        }

        if (ratingFilter !== "") {

            data = data.filter(

                review =>

                    Number(review.rating) ===

                    Number(ratingFilter)

            );

        }

        setFilteredReviews(data);

        setCurrentPage(1);

    }, [reviews, search, ratingFilter]);

    // =====================================
    // VIEW REVIEW
    // =====================================

    const viewReview = (review) => {

        setSelectedReview(review);

        setShowDetails(true);

    };

    // =====================================
    // DELETE REVIEW
    // =====================================

    const confirmDelete = (review) => {

        setReviewToDelete(review);

        setShowDelete(true);

    };

    const deleteReview = async () => {

        try {

            await ReviewService.deleteReview(

                reviewToDelete.id

            );

            setShowDelete(false);

            loadReviews();

        }

        catch (error) {

            console.log(error);

        }

    };
        // =====================================
    // PAGINATION
    // =====================================

    const indexOfLastReview =
        currentPage * reviewsPerPage;

    const indexOfFirstReview =
        indexOfLastReview - reviewsPerPage;

    const currentReviews =
        filteredReviews.slice(
            indexOfFirstReview,
            indexOfLastReview
        );

    const totalPages =
        Math.ceil(
            filteredReviews.length /
            reviewsPerPage
        );

    // =====================================
    // JSX
    // =====================================

    return (

        <Container fluid className="mt-4">

            <Row>

                <Col>

                    <Card className="shadow border-0">

                        <Card.Header className="bg-dark text-white">

                            <div className="d-flex justify-content-between align-items-center">

                                <div>

                                    <h3 className="mb-0">

                                        Review Management

                                    </h3>

                                    <small>

                                        Total Reviews :

                                        <strong className="ms-2">

                                            {filteredReviews.length}

                                        </strong>

                                    </small>

                                </div>

                            </div>

                        </Card.Header>

                        <Card.Body>

                            <Row className="mb-4">

                                <Col lg={5} md={6}>

                                    <InputGroup>

                                        <InputGroup.Text>

                                            <FaSearch />

                                        </InputGroup.Text>

                                        <Form.Control

                                            placeholder="Search reviews..."

                                            value={search}

                                            onChange={(e) =>
                                                setSearch(
                                                    e.target.value
                                                )
                                            }

                                        />

                                    </InputGroup>

                                </Col>

                                <Col lg={3} md={4}>

                                    <Form.Select

                                        value={ratingFilter}

                                        onChange={(e) =>
                                            setRatingFilter(
                                                e.target.value
                                            )
                                        }

                                    >

                                        <option value="">

                                            All Ratings

                                        </option>

                                        <option value="5">

                                            ⭐⭐⭐⭐⭐

                                        </option>

                                        <option value="4">

                                            ⭐⭐⭐⭐

                                        </option>

                                        <option value="3">

                                            ⭐⭐⭐

                                        </option>

                                        <option value="2">

                                            ⭐⭐

                                        </option>

                                        <option value="1">

                                            ⭐

                                        </option>

                                    </Form.Select>

                                </Col>

                            </Row>

                            {

                                loading ?

                                (

                                    <div className="text-center py-5">

                                        <Spinner

                                            animation="border"

                                            variant="primary"

                                        />

                                    </div>

                                )

                                :

                                (

                                    <Table

                                        bordered

                                        hover

                                        responsive

                                        className="align-middle">

                                        <thead className="table-dark">

                                            <tr>

                                                <th>#</th>

                                                <th>Movie</th>

                                                <th>User</th>

                                                <th>Rating</th>

                                                <th>Review</th>

                                                <th>Date</th>

                                                <th width="180">

                                                    Actions

                                                </th>

                                            </tr>

                                        </thead>

                                        <tbody>

                                            {

                                                currentReviews.length === 0 ?

                                                (

                                                    <tr>

                                                        <td

                                                            colSpan="7"

                                                            className="text-center">

                                                            No Reviews Found

                                                        </td>

                                                    </tr>

                                                )

                                                :

                                                currentReviews.map(

                                                    (

                                                        review,

                                                        index

                                                    ) => (

                                                        <tr key={review.id}>

                                                            <td>

                                                                {

                                                                    indexOfFirstReview +

                                                                    index +

                                                                    1

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    review.movie?.name ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    review.user?.username ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                <Badge

                                                                    bg="warning"

                                                                    text="dark">

                                                                    <FaStar className="me-1" />

                                                                    {

// � FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                                                                        review.rating

                                                                    }

                                                                </Badge>

                                                            </td>

                                                            <td>

                                                                {

                                                                    review.comment?.length > 40

                                                                        ?

                                                                        review.comment.substring(0,40) + "..."

                                                                        :

                                                                        review.comment

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    review.createdAt ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                <div className="d-flex gap-2">

                                                                    <Button

                                                                        variant="info"

                                                                        size="sm"

                                                                        onClick={() =>
                                                                            viewReview(review)
                                                                        }

                                                                    >

                                                                        <FaEye />

                                                                    </Button>

                                                                    <Button

                                                                        variant="danger"

                                                                        size="sm"

                                                                        onClick={() =>
                                                                            confirmDelete(review)
                                                                        }

                                                                    >

                                                                        <FaTrash />

                                                                    </Button>

                                                                </div>

                                                            </td>

                                                        </tr>

                                                    )

                                                )

                                            }

                                        </tbody>

                                    </Table>

                                )

                            }                            {/* =====================================
                                PAGINATION
                            ===================================== */}

                            {

                                totalPages > 1 && (

                                    <div className="d-flex justify-content-center mt-4">

                                        <Pagination>

                                            <Pagination.First

                                                disabled={currentPage === 1}

                                                onClick={() =>
                                                    setCurrentPage(1)
                                                }

                                            />

                                            <Pagination.Prev

                                                disabled={currentPage === 1}

                                                onClick={() =>
                                                    setCurrentPage(
                                                        currentPage - 1
                                                    )
                                                }

                                            />

                                            {

                                                [...Array(totalPages)].map(

                                                    (_, i) => (

                                                        <Pagination.Item

                                                            key={i}

                                                            active={
                                                                currentPage ===
                                                                i + 1
                                                            }

                                                            onClick={() =>
                                                                setCurrentPage(
                                                                    i + 1
                                                                )
                                                            }

                                                        >

                                                            {i + 1}

                                                        </Pagination.Item>

                                                    )

                                                )

                                            }

                                            <Pagination.Next

                                                disabled={
                                                    currentPage === totalPages
                                                }

                                                onClick={() =>
                                                    setCurrentPage(
                                                        currentPage + 1
                                                    )
                                                }

                                            />

                                            <Pagination.Last

                                                disabled={
                                                    currentPage === totalPages
                                                }

                                                onClick={() =>
                                                    setCurrentPage(
                                                        totalPages
                                                    )
                                                }

                                            />

                                        </Pagination>

                                    </div>

                                )

                            }

                        </Card.Body>

                    </Card>

                </Col>

            </Row>

            {/* =====================================
                REVIEW DETAILS MODAL
            ===================================== */}

            <Modal

                show={showDetails}

                onHide={() => setShowDetails(false)}

                centered

                size="lg"

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        Review Details

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        selectedReview && (

                            <>

                                <Row className="mb-3">

                                    <Col md={6}>

                                        <strong>Movie</strong>

                                        <div>

                                            {

                                                selectedReview.movie?.name ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                    <Col md={6}>

                                        <strong>User</strong>

                                        <div>

                                            {

                                                selectedReview.user?.username ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                </Row>

                                <Row className="mb-3">

                                    <Col md={6}>

                                        <strong>Rating</strong>

                                        <div>

                                            <Badge

                                                bg="warning"

                                                text="dark">

                                                ⭐ {

                                                    selectedReview.rating

                                                }

                                            </Badge>

                                        </div>

                                    </Col>

                                    <Col md={6}>

                                        <strong>Date</strong>

                                        <div>

                                            {

                                                selectedReview.createdAt ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                </Row>

                                <hr />

                                <strong>

                                    Review

                                </strong>

                                <div

                                    className="border rounded p-3 bg-light mt-2"

                                    style={{

                                        minHeight: "140px"

                                    }}

                                >

                                    {

                                        selectedReview.comment

                                    }

                                </div>

                            </>

                        )

                    }

                </Modal.Body>

                <Modal.Footer>

                    <Button

                        variant="secondary"

                        onClick={() =>
                            setShowDetails(false)
                        }

                    >

                        Close

                    </Button>

                </Modal.Footer>

            </Modal>

            {/* =====================================
                DELETE REVIEW MODAL
            ===================================== */}

            <Modal

                show={showDelete}

                onHide={() =>
                    setShowDelete(false)
                }

                centered

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        Delete Review

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        reviewToDelete && (

                            <>

                                <h5>

                                    Delete this review?

                                </h5>

                                <p>

                                    Review by

                                    <strong>

                                        {" "}

                                        {

                                            reviewToDelete.user?.username

                                        }

                                    </strong>

                                    {" "}for{" "}

                                    <strong>

                                        {

                                            reviewToDelete.movie?.name

                                        }

                                    </strong>

                                    .

                                </p>

                            </>

                        )

                    }

                </Modal.Body>

                <Modal.Footer>

                    <Button

                        variant="secondary"

                        onClick={() =>
                            setShowDelete(false)
                        }

                    >

                        Cancel

                    </Button>

                    <Button

                        variant="danger"

                        onClick={deleteReview}

                    >

                        Delete Review

                    </Button>

                </Modal.Footer>

            </Modal>

        </Container>

    );

};

export default ManageReviews;
