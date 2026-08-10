import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const AddMovie = () => {

    const [movie, setMovie] = useState({
        title: "",
        genre: "",
        language: "",
        releaseDate: "",
        description: ""
    });

    const handleChange = (e) => {
        setMovie({
            ...movie,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(movie);
        alert("Movie Added Successfully");
    };

    return (
        <Container className="mt-4">
            <Card className="shadow">
                <Card.Header>
                    <h3>Add Movie</h3>
                </Card.Header>

                <Card.Body>

                    <Form onSubmit={handleSubmit}>

                        <Row>

                            <Col md={6}>
                                <Form.Group className="mb-3">
                                    <Form.Label>Movie Title</Form.Label>
                                    <Form.Control
                                        name="title"
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </Col>

                            <Col md={6}>
                                <Form.Group className="mb-3">
                                    <Form.Label>Genre</Form.Label>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                                    <Form.Control
                                        name="genre"
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </Col>

                        </Row>

                        <Row>

                            <Col md={6}>
                                <Form.Group className="mb-3">
                                    <Form.Label>Language</Form.Label>
                                    <Form.Control
                                        name="language"
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </Col>

                            <Col md={6}>
                                <Form.Group className="mb-3">
                                    <Form.Label>Release Date</Form.Label>
                                    <Form.Control
                                        type="date"
                                        name="releaseDate"
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </Col>

                        </Row>

                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={5}
                                name="description"
                                onChange={handleChange}
                            />
                        </Form.Group>

                        <Button type="submit">
                            Save Movie
                        </Button>

                    </Form>

                </Card.Body>
            </Card>
        </Container>
    );
};

export default AddMovie;
