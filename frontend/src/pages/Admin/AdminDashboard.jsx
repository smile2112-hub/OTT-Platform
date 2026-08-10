import React from "react";
import { Card, Col, Container, Row } from "react-bootstrap";

const AdminDashboard = () => {
    return (
        <Container className="mt-4">
            <h2 className="mb-4">Admin Dashboard</h2>

            <Row>
                <Col md={3}>
                    <Card className="text-center shadow">
                        <Card.Body>
                            <h5>Movies</h5>
                            <h2>0</h2>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={3}>
                    <Card className="text-center shadow">
                        <Card.Body>
                            <h5>Users</h5>
                            <h2>0</h2>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                        </Card.Body>
                    </Card>
                </Col>

                <Col md={3}>
                    <Card className="text-center shadow">
                        <Card.Body>
                            <h5>Reviews</h5>
                            <h2>0</h2>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={3}>
                    <Card className="text-center shadow">
                        <Card.Body>
                            <h5>Subscriptions</h5>
                            <h2>0</h2>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default AdminDashboard;
