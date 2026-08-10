import React, { useEffect, useState } from "react";
import {
    Alert,
    Badge,
    Button,
    Card,
    Col,
    Container,
    Row,
    Spinner,
    Table
} from "react-bootstrap";
import SubscriptionService from "../services/SubscriptionService";

const plans = [
    { key: "BASIC", label: "Basic", price: 199 },
    { key: "STANDARD", label: "Standard", price: 399 },
    { key: "PREMIUM", label: "Premium", price: 699 },
    { key: "VIP", label: "VIP", price: 999 }
];

const Subscription = () => {

    const [subscription, setSubscription] = useState(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const loadSubscription = async () => {
        try {
            setLoading(true);
            const response = await SubscriptionService.getMySubscription();
            setSubscription(response.data);
        }
        catch (err) {
            setSubscription(null);
            console.log(err);
        }
        finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadSubscription();
    }, []);

    const handleAction = async (action, plan) => {
        setError("");
        setMessage("");
        setSaving(true);

        try {
            let response;

            if (action === "purchase") {
                response = await SubscriptionService.purchaseSubscription(plan);
            }
            else if (action === "upgrade") {
                response = await SubscriptionService.upgradeSubscription(plan);
            }
            else if (action === "cancel") {
                response = await SubscriptionService.cancelSubscription();
            }

            if (response && response.data) {
                setMessage(response.data);
            }

            await loadSubscription();
        }
        catch (err) {
            console.log(err);
            setError(
                err?.response?.data ||
                err?.message ||
                "Unable to update subscription"
            );
        }
        finally {
            setSaving(false);
        }
    };

    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" variant="danger" />
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <Row className="justify-content-center">
                <Col lg={10}>
                    <Card className="shadow bg-dark text-white">
                        <Card.Header className="bg-dark text-white">
                            <h3 className="mb-0">Subscription</h3>
                        </Card.Header>
                        <Card.Body>
                            {message && <Alert variant="success">{message}</Alert>}
                            {error && <Alert variant="danger">{error}</Alert>}

                            <Row className="mb-4">
                                <Col md={4}>
                                    <Card className="mb-3 shadow-sm bg-dark text-white">
                                        <Card.Body>
                                            <Card.Title>Current Plan</Card.Title>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                                            <p className="mb-1 text-white">
                                                <strong>Plan:</strong>
                                                {' '}
                                                {subscription?.plan || "Free"}
                                            </p>
                                            <p className="mb-1 text-white">
                                                <strong>Status:</strong>
                                                {' '}
                                                {subscription?.active ? (
                                                    <Badge bg="success">Active</Badge>
                                                ) : (
                                                    <Badge bg="secondary">Inactive</Badge>
                                                )}
                                            </p>
                                            {subscription?.startDate && (
                                                <p className="mb-1 text-white">
                                                    <strong>Start:</strong>
                                                    {' '}
                                                    {subscription.startDate}
                                                </p>
                                            )}
                                            {subscription?.expiryDate && (
                                                <p className="mb-0 text-white">
                                                    <strong>Expiry:</strong>
                                                    {' '}
                                                    {subscription.expiryDate}
                                                </p>
                                            )}
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col md={8}>
                                    <Card className="mb-3 shadow-sm bg-dark text-white">
                                        <Card.Body>
                                            <Card.Title>Subscription Benefits</Card.Title>
                                            <p className="text-white">
                                                Upgrade to Premium or VIP for access to higher quality content, priority support, and extended features.
                                            </p>
                                            <p className="text-white">
                                                Purchase or upgrade anytime. You can also cancel your current subscription here.
                                            </p>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>

                            <Card className="mb-4 shadow-sm bg-dark text-white">
                                <Card.Header className="bg-dark text-white">
                                    <h5 className="mb-0">Available Plans</h5>
                                </Card.Header>
                                <Card.Body>
                                    <Table responsive bordered hover variant="dark">
                                        <thead>
                                            <tr>
                                                <th>Plan</th>
                                                <th>Price</th>
                                                <th>Validity</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {plans.map((plan) => (
                                                <tr key={plan.key}>
                                                    <td>{plan.label}</td>
                                                    <td>â‚¹ {plan.price}</td>
                                                    <td>30 days</td>
                                                    <td>
                                                        <Button
                                                            variant={
                                                                subscription?.plan === plan.key
                                                                    ? "secondary"
                                                                    : "danger"
                                                            }
                                                            disabled={saving}
                                                            onClick={() =>
                                                                handleAction(
                                                                    subscription?.plan === plan.key
                                                                        ? "upgrade"
                                                                        : "purchase",
                                                                    plan.key
                                                                )
                                                            }
                                                        >
                                                            {subscription?.plan === plan.key
                                                                ? "Renew / Upgrade"
                                                                : "Select"}
                                                        </Button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </Table>
                                </Card.Body>
                            </Card>

                            {subscription?.active && (
                                <div className="text-end">
                                    <Button
                                        variant="outline-danger"
                                        disabled={saving}
                                        onClick={() => handleAction("cancel")}
                                    >
                                        Cancel Subscription
                                    </Button>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Subscription;
