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
import PaymentService from "../services/PaymentService";
import SubscriptionService from "../services/SubscriptionService";

const Payment = () => {

    const [subscription, setSubscription] = useState(null);
    const [payments, setPayments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [paymentRef, setPaymentRef] = useState("");
    const [upiId, setUpiId] = useState("flowflix@upi");
    const [upiLink, setUpiLink] = useState(null);
    const [upiQrBase64, setUpiQrBase64] = useState(null);
    const [activationReference, setActivationReference] = useState(null);
    const [activationMessage, setActivationMessage] = useState(null);

    const loadData = async () => {
        try {
            setLoading(true);
            const subscriptionResponse = await SubscriptionService.getMySubscription();
            setSubscription(subscriptionResponse.data);
            const paymentResponse = await PaymentService.getMyPayments();
            setPayments(paymentResponse.data);
        }
        catch (err) {
            console.log(err);
            setError("Unable to load payment information");
        }
        finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const createPayment = async () => {
        if (!subscription?.id) {
            setError("You must have a subscription before creating a payment.");
            return;
        }

        setSaving(true);
        setError("");
        setMessage("");

        try {
            const response = await PaymentService.createPayment(subscription.id);
            const data = response.data || {};

            setMessage(data.id ? "Payment request created successfully." : "Payment request created.");

            // populate UPI / activation details returned by server
            setUpiLink(data.upiLink || null);
            setUpiQrBase64(data.upiQrBase64 || null);
            setActivationReference(data.activationReference || null);
            setActivationMessage(data.activationMessage || null);

            // if server provided an activationReference, use it as the paymentRef
            if (data.activationReference) {
                setPaymentRef(data.activationReference);
            }

            await loadData();
        }
        catch (err) {
            console.log(err);
            setError(err?.response?.data || "Unable to create payment.");
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
                    <Card className="shadow">
                        <Card.Header className="bg-dark text-white">
                            <h3 className="mb-0">Payments</h3>
                        </Card.Header>
                        <Card.Body>
                            {message && <Alert variant="success">{message}</Alert>}
                            {error && <Alert variant="danger">{error}</Alert>}

                            <Row className="mb-4">
                                <Col md={6}>
                                    <Card className="mb-3">
                                        <Card.Body>
                                            <Card.Title>Subscription</Card.Title>
                                            <p className="mb-1">
                                                <strong>Plan:</strong>
                                                {' '}
                                                {subscription?.plan || "Free"}
                                            </p>


                                            <p className="mb-1">
                                                <strong>Status:</strong>
                                                {' '}
                                                {subscription?.active ? (
                                                    <Badge bg="success">Active</Badge>
                                                ) : (
                                                    <Badge bg="secondary">Inactive</Badge>
                                                )}
                                            </p>
                                            <p className="mb-1">
                                                <strong>Expiry:</strong>
                                                {' '}
                                                {subscription?.expiryDate || "N/A"}
                                            </p>
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col md={6} className="d-flex align-items-center justify-content-end">
                                    <Button
                                        variant="danger"
                                        disabled={saving || !subscription?.id}
                                        onClick={() => {
                                            setPaymentRef(`FLOWFLIX-UPI-${Date.now()}-${Math.floor(Math.random() * 9000 + 1000)}`);
                                            createPayment();
                                        }}
                                    >
                                        {saving ? "Creating..." : "Create Payment"}
                                    </Button>
                                </Col>
                            </Row>

                            <Card>
                                <Card.Header>
                        {paymentRef && (
                            <Card className="mb-3">
                                <Card.Body>
                                    <Card.Title>UPI Payment Details</Card.Title>
                                    <p className="mb-1">
                                        <strong>UPI ID:</strong>
                                        {' '}
                                        {upiId}
                                    </p>
                                    {upiLink && (
                                        <p className="mb-1">
                                            <strong>UPI Link:</strong>
                                            {' '}
                                            <a href={upiLink}>{upiLink}</a>
                                        </p>
                                    )}
                                    {upiQrBase64 && (
                                        <p className="mb-2">
                                            <img alt="UPI QR" src={`data:image/png;base64,${upiQrBase64}`} style={{maxWidth: 220}}/>
                                        </p>
                                    )}
                                    <p className="mb-1">
                                        <strong>Payment Reference:</strong>
                                        {' '}
                                        {paymentRef}
                                    </p>
                                    {activationMessage && (
                                        <p className="mb-0 text-muted">{activationMessage}</p>
                                    )}
                                    <div className="mt-3">
                                        <Button variant="success" onClick={async () => {
                                            try {
                                                setSaving(true);
                                                const idToSend = paymentRef || activationReference || 'any';
                                                const res = await PaymentService.verifyAny(idToSend);
                                                setMessage(res.data || 'Payment verified');
                                                await loadData();
                                            } catch (e) {
                                                setError('Unable to verify payment');
                                            } finally {
                                                setSaving(false);
                                            }
                                        }}>Mark Paid (Verify)</Button>
                                    </div>
                                </Card.Body>
                            </Card>
                        )}
                                    <h5 className="mb-0">Payment History</h5>
                                </Card.Header>
                                <Card.Body>
                                    {payments.length === 0 ? (
                                        <div className="text-center py-4">
                                            No payments found.
                                        </div>
                                    ) : (
                                        <Table responsive bordered hover>
                                            <thead className="table-dark">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Plan</th>
                                                    <th>Amount</th>
                                                    <th>Status</th>
                                                    <th>Date</th>
                                                    <th>Transaction</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {payments.map((payment) => (
                                                    <tr key={payment.id}>
                                                        <td>{payment.id}</td>
                                                        <td>{payment.subscriptionPlan || "N/A"}</td>
                                                        <td>₹ {payment.amount || 0}</td>
                                                        <td>{payment.paymentStatus || "N/A"}</td>
                                                        <td>{payment.paymentDate ? new Date(payment.paymentDate).toLocaleString() : "-"}</td>
                                                        <td>{payment.transactionId || "-"}</td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </Table>
                                    )}
                                </Card.Body>
                            </Card>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Payment;
