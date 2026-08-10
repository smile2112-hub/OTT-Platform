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
    FaCheckCircle,
    FaTimesCircle
} from "react-icons/fa";

import SubscriptionService from "../../services/SubscriptionService";

const Subscriptions = () => {

    // =====================================
    // STATES
    // =====================================

    const [subscriptions, setSubscriptions] = useState([]);

    const [filteredSubscriptions, setFilteredSubscriptions] = useState([]);

    const [loading, setLoading] = useState(true);

    const [search, setSearch] = useState("");

    const [selectedSubscription, setSelectedSubscription] = useState(null);

    const [showDetails, setShowDetails] = useState(false);

    const [showDelete, setShowDelete] = useState(false);

    const [subscriptionToDelete, setSubscriptionToDelete] = useState(null);

    const [currentPage, setCurrentPage] = useState(1);

    const subscriptionsPerPage = 10;

    // =====================================
    // LOAD SUBSCRIPTIONS
    // =====================================

    useEffect(() => {

        loadSubscriptions();

    }, []);

    const loadSubscriptions = async () => {

        try {

            setLoading(true);

            const response =
                await SubscriptionService.getAllSubscriptions();

            setSubscriptions(response.data);

            setFilteredSubscriptions(response.data);

        }

        catch (error) {

            console.log(error);

        }

        finally {

            setLoading(false);

        }

    };

    // =====================================
    // SEARCH
    // =====================================

    useEffect(() => {

        if (search.trim() === "") {

            setFilteredSubscriptions(subscriptions);

            return;

        }

        const keyword = search.toLowerCase();

        const result = subscriptions.filter((subscription) =>

            (subscription.user?.username || "")
                .toLowerCase()
                .includes(keyword)

            ||

            (subscription.planName || "")
                .toLowerCase()
                .includes(keyword)

            ||

            (subscription.user?.email || "")
                .toLowerCase()
                .includes(keyword)

        );

        setFilteredSubscriptions(result);

        setCurrentPage(1);

    }, [subscriptions, search]);

    // =====================================
    // VIEW DETAILS
    // =====================================

    const viewSubscription = (subscription) => {

        setSelectedSubscription(subscription);

        setShowDetails(true);

    };

    // =====================================
    // DELETE
    // =====================================

    const confirmDelete = (subscription) => {

        setSubscriptionToDelete(subscription);

        setShowDelete(true);

    };

    const deleteSubscription = async () => {

        try {

            await SubscriptionService.deleteSubscription(

                subscriptionToDelete.id

            );

            setShowDelete(false);

            loadSubscriptions();

        }

        catch (error) {

            console.log(error);

        }

    };
        // =====================================
    // PAGINATION
    // =====================================

    const indexOfLastSubscription =
        currentPage * subscriptionsPerPage;

    const indexOfFirstSubscription =
        indexOfLastSubscription - subscriptionsPerPage;

    const currentSubscriptions =
        filteredSubscriptions.slice(
            indexOfFirstSubscription,
            indexOfLastSubscription
        );

    const totalPages =
        Math.ceil(
            filteredSubscriptions.length /
            subscriptionsPerPage
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

                                        Subscription Management

                                    </h3>

                                    <small>

                                        Total Subscriptions :

                                        <strong className="ms-2">

                                            {filteredSubscriptions.length}

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

                                            placeholder="Search subscription..."

                                            value={search}

                                            onChange={(e) =>
                                                setSearch(
                                                    e.target.value
                                                )
                                            }

                                        />

                                    </InputGroup>

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

                                                <th>User</th>

                                                <th>Email</th>

                                                <th>Plan</th>

                                                <th>Price</th>

                                                <th>Start Date</th>

                                                <th>End Date</th>

                                                <th>Status</th>

                                                <th width="180">

                                                    Actions

                                                </th>

                                            </tr>

                                        </thead>

                                        <tbody>

                                            {

                                                currentSubscriptions.length === 0 ?

                                                (

                                                    <tr>

                                                        <td

                                                            colSpan="9"

                                                            className="text-center">

                                                            No Subscriptions Found

                                                        </td>

                                                    </tr>

                                                )

                                                :

                                                currentSubscriptions.map(

                                                    (

                                                        subscription,

                                                        index

                                                    ) => (

                                                        <tr key={subscription.id}>

                                                            <td>

                                                                {

                                                                    indexOfFirstSubscription +

                                                                    index +

                                                                    1

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    subscription.user?.username ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    subscription.user?.email ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                <Badge bg="primary">

                                                                    {

                                                                        subscription.planName ||

                                                                        "Basic"

                                                                    }

                                                                </Badge>

                                                            </td>

                                                            <td>

                                                                ₹ {

                                                                    subscription.price ||

                                                                    0

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    subscription.startDate ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    subscription.endDate ||

                                                                    "-"

                                                                }

                                                            </td>

                                                            <td>

                                                                {

                                                                    subscription.active ?

                                                                    (

                                                                        <Badge bg="success">

                                                                            <FaCheckCircle className="me-1"/>

                                                                            Active

                                                                        </Badge>

                                                                    )

                                                                    :

                                                                    (

                                                                        <Badge bg="danger">

                                                                            <FaTimesCircle className="me-1"/>

                                                                            Expired

                                                                        </Badge>

                                                                    )

                                                                }


// � FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                                                            </td>

                                                            <td>

                                                                <div className="d-flex gap-2">

                                                                    <Button

                                                                        variant="info"

                                                                        size="sm"

                                                                        onClick={() =>
                                                                            viewSubscription(subscription)
                                                                        }

                                                                    >

                                                                        <FaEye />

                                                                    </Button>

                                                                    <Button

                                                                        variant="danger"

                                                                        size="sm"

                                                                        onClick={() =>
                                                                            confirmDelete(subscription)
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

                            }
                                                        {/* =====================================
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
                SUBSCRIPTION DETAILS MODAL
            ===================================== */}

            <Modal

                show={showDetails}

                onHide={() => setShowDetails(false)}

                centered

                size="lg"

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        Subscription Details

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        selectedSubscription && (

                            <>

                                <Row className="mb-3">

                                    <Col md={6}>

                                        <strong>User</strong>

                                        <div>

                                            {

                                                selectedSubscription.user?.username ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                    <Col md={6}>

                                        <strong>Email</strong>

                                        <div>

                                            {

                                                selectedSubscription.user?.email ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                </Row>

                                <Row className="mb-3">

                                    <Col md={6}>

                                        <strong>Plan</strong>

                                        <div>

                                            {

                                                selectedSubscription.planName ||

                                                "Basic"

                                            }

                                        </div>

                                    </Col>

                                    <Col md={6}>

                                        <strong>Price</strong>

                                        <div>

                                            ₹ {

                                                selectedSubscription.price ||

                                                0

                                            }

                                        </div>

                                    </Col>

                                </Row>

                                <Row className="mb-3">

                                    <Col md={6}>

                                        <strong>Start Date</strong>

                                        <div>

                                            {

                                                selectedSubscription.startDate ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                    <Col md={6}>

                                        <strong>End Date</strong>

                                        <div>

                                            {

                                                selectedSubscription.endDate ||

                                                "-"

                                            }

                                        </div>

                                    </Col>

                                </Row>

                                <Row>

                                    <Col>

                                        <strong>Status</strong>

                                        <div className="mt-2">

                                            {

                                                selectedSubscription.active ?

                                                (

                                                    <Badge bg="success">

                                                        Active

                                                    </Badge>

                                                )

                                                :

                                                (

                                                    <Badge bg="danger">

                                                        Expired

                                                    </Badge>

                                                )

                                            }

                                        </div>

                                    </Col>

                                </Row>

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
                DELETE CONFIRMATION MODAL
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

                        Delete Subscription

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        subscriptionToDelete && (

                            <>

                                <h5>

                                    Are you sure?

                                </h5>

                                <p>

                                    Delete

                                    <strong>

                                        {" "}

                                        {

                                            subscriptionToDelete.planName

                                        }

                                    </strong>

                                    {" "}subscription of{" "}

                                    <strong>

                                        {

                                            subscriptionToDelete.user?.username

                                        }

                                    </strong>

                                    ?

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

                        onClick={deleteSubscription}

                    >

                        Delete

                    </Button>

                </Modal.Footer>

            </Modal>

        </Container>

    );

};

export default Subscriptions;
