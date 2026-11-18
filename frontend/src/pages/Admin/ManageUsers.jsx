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
    FaCheckCircle,
    FaEye,
    FaSearch,
    FaTrash,
    FaTimesCircle,
    FaUserShield,
    FaUser
} from "react-icons/fa";

import UserService from "../../services/UserService";

const ManageUsers = () => {

    // =====================================
    // STATES
    // =====================================

    const [users, setUsers] = useState([]);

    const [filteredUsers, setFilteredUsers] = useState([]);

    const [loading, setLoading] = useState(true);

    const [search, setSearch] = useState("");

    const [selectedUser, setSelectedUser] = useState(null);

    const [showDetails, setShowDetails] = useState(false);

    const [showDelete, setShowDelete] = useState(false);

    const [userToDelete, setUserToDelete] = useState(null);

    const [currentPage, setCurrentPage] = useState(1);

    const usersPerPage = 10;

    // =====================================
    // LOAD USERS
    // =====================================

    useEffect(() => {

        loadUsers();

    }, []);

    const loadUsers = async () => {

        try {

            setLoading(true);

            const response =
                await UserService.getAllUsers();

            setUsers(response.data);

            setFilteredUsers(response.data);

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

            setFilteredUsers(users);

            return;

        }

        const keyword = search.toLowerCase();

        const result = users.filter((user) =>

            (user.nameSurname || "")
                .toLowerCase()
                .includes(keyword)

            ||

            (user.username || "")
                .toLowerCase()
                .includes(keyword)

            ||

            (user.email || "")
                .toLowerCase()
                .includes(keyword)

        );

        setFilteredUsers(result);

        setCurrentPage(1);

    }, [search, users]);

    // =====================================
    // VIEW DETAILS
    // =====================================

    const viewUser = (user) => {

        setSelectedUser(user);

        setShowDetails(true);

    };

    // =====================================
    // DELETE
    // =====================================

    const confirmDelete = (user) => {

        setUserToDelete(user);

        setShowDelete(true);

    };

    const deleteUser = async () => {

        try {

            await UserService.deleteUser(userToDelete.id);

            setShowDelete(false);

            loadUsers();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // ACTIVATE
    // =====================================

    const activateUser = async (id) => {

        try {

            await UserService.activateUser(id);

            loadUsers();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // DEACTIVATE
    // =====================================

    const deactivateUser = async (id) => {

        try {

            await UserService.deactivateUser(id);

            loadUsers();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // MAKE ADMIN
    // =====================================

    const makeAdmin = async (id) => {

        try {

            await UserService.makeAdmin(id);

            loadUsers();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // REMOVE ADMIN
    // =====================================

    const removeAdmin = async (id) => {

        try {

            await UserService.removeAdmin(id);

            loadUsers();

        }

        catch (error) {

            console.log(error);

        }

    };    // =====================================
    // PAGINATION
    // =====================================

    const indexOfLastUser =
        currentPage * usersPerPage;

    const indexOfFirstUser =
        indexOfLastUser - usersPerPage;

    const currentUsers =
        filteredUsers.slice(
            indexOfFirstUser,
            indexOfLastUser
        );

    const totalPages =
        Math.ceil(
            filteredUsers.length /
            usersPerPage
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

                                        User Management

                                    </h3>

                                    <small>

                                        Total Users :

                                        <strong className="ms-2">

                                            {

                                                filteredUsers.length

                                            }

                                        </strong>

                                    </small>

                                </div>

                            </div>

                        </Card.Header>

                        <Card.Body>

                            {/* =====================================
                                SEARCH
                            ===================================== */}

                            <Row className="mb-4">

                                <Col lg={5} md={6}>

                                    <InputGroup>

                                        <InputGroup.Text>

                                            <FaSearch />

                                        </InputGroup.Text>

                                        <Form.Control

                                            placeholder="Search user..."

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

                            {/* =====================================
                                LOADING
                            ===================================== */}

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

                                            hover

                                            bordered

                                            responsive

                                            className="align-middle">

                                            <thead className="table-dark">

                                                <tr>

                                                    <th>#</th>

                                                    <th>Name</th>

                                                    <th>Username</th>

                                                    <th>Email</th>

                                                    <th>Role</th>

                                                    <th>Status</th>

                                                    <th width="320">

                                                        Actions

                                                    </th>

                                                </tr>

                                            </thead>

                                            <tbody>

                                                {

                                                    currentUsers.length === 0 ?

                                                        (

                                                            <tr>

                                                                <td

                                                                    colSpan="7"

                                                                    className="text-center">

                                                                    No Users Found

                                                                </td>

                                                            </tr>

                                                        )

                                                        :

                                                        currentUsers.map(

                                                            (

                                                                user,

                                                                index

                                                            ) => (

                                                                <tr key={user.id}>

                                                                    <td>

                                                                        {

                                                                            indexOfFirstUser +

                                                                            index +

                                                                            1

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        {

                                                                            user.nameSurname

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        {

                                                                            user.username

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        {

                                                                            user.email

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        {

                                                                            user.role === "ADMIN"

                                                                                ?

                                                                                (

                                                                                    <Badge bg="danger">

                                                                                        ADMIN

                                                                                    </Badge>

                                                                                )

                                                                                :



                                                                                (

                                                                                    <Badge bg="secondary">

                                                                                        USER

                                                                                    </Badge>

                                                                                )

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        {

                                                                            user.deleted ?

                                                                                (

                                                                                    <Badge bg="danger">

                                                                                        Inactive

                                                                                    </Badge>

                                                                                )

                                                                                :

                                                                                (

                                                                                    <Badge bg="success">

                                                                                        Active

                                                                                    </Badge>

                                                                                )

                                                                        }

                                                                    </td>

                                                                    <td>

                                                                        <div className="d-flex flex-wrap gap-2">                                                                            {/* =====================================
                                                                                VIEW USER
                                                                            ===================================== */}

                                                                            <Button

                                                                                variant="info"

                                                                                size="sm"

                                                                                title="View User"

                                                                                onClick={() =>
                                                                                    viewUser(user)
                                                                                }

                                                                            >

                                                                                <FaEye />

                                                                            </Button>

                                                                            {/* =====================================
                                                                                DELETE USER
                                                                            ===================================== */}

                                                                            <Button

                                                                                variant="danger"

                                                                                size="sm"

                                                                                title="Delete User"

                                                                                onClick={() =>
                                                                                    confirmDelete(user)
                                                                                }

                                                                            >

                                                                                <FaTrash />

                                                                            </Button>

                                                                            {/* =====================================
                                                                                ACTIVATE / DEACTIVATE
                                                                            ===================================== */}

                                                                            {

                                                                                user.deleted ?

                                                                                    (

                                                                                        <Button

                                                                                            variant="success"

                                                                                            size="sm"

                                                                                            title="Activate User"

                                                                                            onClick={() =>
                                                                                                activateUser(user.id)
                                                                                            }

                                                                                        >

                                                                                            <FaCheckCircle />

                                                                                        </Button>

                                                                                    )

                                                                                    :

                                                                                    (

                                                                                        <Button

                                                                                            variant="warning"

                                                                                            size="sm"

                                                                                            title="Deactivate User"

                                                                                            onClick={() =>
                                                                                                deactivateUser(user.id)
                                                                                            }

                                                                                        >

                                                                                            <FaTimesCircle />

                                                                                        </Button>

                                                                                    )

                                                                            }

                                                                            {/* =====================================
                                                                                MAKE / REMOVE ADMIN
                                                                            ===================================== */}

                                                                            {

                                                                                user.role === "ADMIN" ?

                                                                                    (

                                                                                        <Button

                                                                                            variant="secondary"

                                                                                            size="sm"

                                                                                            title="Remove Admin"

                                                                                            onClick={() =>
                                                                                                removeAdmin(user.id)
                                                                                            }

                                                                                        >

                                                                                            <FaUser />

                                                                                        </Button>

                                                                                    )

                                                                                    :

                                                                                    (

                                                                                        <Button

                                                                                            variant="primary"

                                                                                            size="sm"

                                                                                            title="Make Admin"

                                                                                            onClick={() =>
                                                                                                makeAdmin(user.id)
                                                                                            }

                                                                                        >

                                                                                            <FaUserShield />

                                                                                        </Button>

                                                                                    )

                                                                            }

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
                USER DETAILS MODAL
            ===================================== */}

            <Modal

                show={showDetails}

                onHide={() => setShowDetails(false)}

                centered

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        User Details

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        selectedUser && (

                            <>

                                <p>

                                    <strong>Name :</strong>{" "}

                                    {selectedUser.nameSurname}

                                </p>

                                <p>

                                    <strong>Username :</strong>{" "}

                                    {selectedUser.username}

                                </p>

                                <p>

                                    <strong>Email :</strong>{" "}

                                    {selectedUser.email}

                                </p>

                                <p>

                                    <strong>Role :</strong>{" "}

                                    {selectedUser.role}

                                </p>

                                <p>

                                    <strong>Status :</strong>{" "}

                                    {

                                        selectedUser.deleted

                                            ? "Inactive"

                                            : "Active"

                                    }

                                </p>

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
                DELETE CONFIRMATION
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

                        Delete User

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        userToDelete && (

                            <>

                                <h5>

                                    Are you sure?

                                </h5>

                                <p>

                                    This action will permanently delete

                                    <strong>

                                        {" "}

                                        {userToDelete.nameSurname}

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

                        onClick={deleteUser}

                    >

                        Delete User

                    </Button>

                </Modal.Footer>

            </Modal>

        </Container>

    );

};

export default ManageUsers;
