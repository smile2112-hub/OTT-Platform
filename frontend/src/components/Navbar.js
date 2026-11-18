import React, { useState } from "react";
import {
  Navbar,
  Nav,
  NavDropdown,
  Container,
  Form,
  FormControl,
  Button
} from "react-bootstrap";

import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const NavigationBar = () => {

    const navigate = useNavigate();
    const { isAuthenticated, user, role, logout } = useAuth();
    const [search, setSearch] = useState("");

    const handleLogout = async () => {
        await logout();
        navigate("/login");
    };

    const handleSearch = (e) => {

        e.preventDefault();

        if (search.trim() !== "") {

            navigate("/search?keyword=" + search);

        }

    };

    return (

        <Navbar bg="dark" variant="dark" expand="lg" sticky="top" className="navbar-custom">

            <Container>

                <Navbar.Brand
                    as={Link}
                    to="/"
                >
                    🎬 FlowFlix
                </Navbar.Brand>

                <Navbar.Toggle />

                <Navbar.Collapse>

                    <Nav className="me-auto">

                        <Nav.Link as={Link} to="/">
                            Home
                        </Nav.Link>

                        <Nav.Link as={Link} to="/search">
                            Search
                        </Nav.Link>
                        <Nav.Link as={Link} to="/favorites">
                            Favorites
                        </Nav.Link>
                        <Nav.Link as={Link} to="/watchlist">
                            My Watchlist
                        </Nav.Link>

                    </Nav>

                    <Form
                        className="d-flex me-3"
                        onSubmit={handleSearch}
                    >

                        <FormControl

                            placeholder="Search Movies"

                            value={search}

                            onChange={(e)=>setSearch(e.target.value)}

                        />

                        <Button
                            type="submit"
                            variant="danger"
                        >
                            Search
                        </Button>

                    </Form>

                    {

                        !isAuthenticated ?

                        (

                            <Nav>

                                <Nav.Link as={Link} to="/login">



                                    Login

                                </Nav.Link>

                                <Nav.Link as={Link} to="/register">

                                    Register

                                </Nav.Link>

                            </Nav>

                        )

                        :

                        (

                            <Nav>

                                <NavDropdown

                                        title={user?.username || "Account"}

                                >

                                    <NavDropdown.Item

                                        as={Link}

                                        to="/profile"

                                    >

                                        Profile

                                    </NavDropdown.Item>

                                    <NavDropdown.Item

                                        as={Link}

                                        to="/subscription"

                                    >

                                        Subscription

                                    </NavDropdown.Item>


                                    {

                                        role === "ADMIN" && (

                                            <>

                                                <NavDropdown.Divider />

                                                <NavDropdown.Item

                                                    as={Link}

                                                    to="/admin"

                                                >

                                                    Admin Dashboard

                                                </NavDropdown.Item>

                                            </>

                                        )

                                    }

                                    <NavDropdown.Divider />

                                    <NavDropdown.Item

                                        onClick={handleLogout}

                                    >

                                        Logout

                                    </NavDropdown.Item>

                                </NavDropdown>

                            </Nav>

                        )

                    }

                </Navbar.Collapse>

            </Container>

        </Navbar>

    );

};

export default NavigationBar;
