import React from "react";
import { Button, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const NotFound = () => {

    return (

        <Container
            className="text-center mt-5"
        >

            <h1
                style={{
                    fontSize: "120px",
                    color: "#dc3545"
                }}
            >
                404
            </h1>

            <h2>


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                Oops!

            </h2>

            <p>

                The page you are looking for does not exist.

            </p>

            <Link to="/">

                <Button variant="danger">

                    Back To Home

                </Button>

            </Link>

        </Container>

    );

};

export default NotFound;
