/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState } from "react";
import SubscriptionService from "../services/SubscriptionService";

const ManageSubscriptions = () => {

    const [subscriptions, setSubscriptions] = useState([]);

    useEffect(() => {

        loadSubscriptions();

    }, []);

    const loadSubscriptions = async () => {

        try {

            const response =
                await SubscriptionService.getAllSubscriptions();

            setSubscriptions(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const activateSubscription = async (id) => {

        try {

            await SubscriptionService.activateSubscription(id);

            loadSubscriptions();

        }

        catch (error) {

            alert("Unable to activate subscription");

        }

    };

    const deactivateSubscription = async (id) => {

        try {

            await SubscriptionService.deactivateSubscription(id);

            loadSubscriptions();

        }

        catch (error) {

            alert("Unable to deactivate subscription");

        }

    };

    const deleteSubscription = async (id) => {

        if (!window.confirm("Delete this subscription?")) {

            return;

        }

        try {

            await SubscriptionService.deleteSubscription(id);

            loadSubscriptions();

        }

        catch (error) {

            alert("Unable to delete subscription");

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">

                Manage Subscriptions

            </h2>

            <div className="table-responsive">

                <table className="table table-bordered table-hover">

                    <thead className="table-dark">

                        <tr>

                            <th>ID</th>

                            <th>User</th>

                            <th>Plan</th>

                            <th>Price</th>

                            <th>Start Date</th>

                            <th>End Date</th>

                            <th>Status</th>

                            <th>Payment</th>

                            <th>Actions</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            subscriptions.length === 0 ?

                                <tr>

                                    <td
                                        colSpan="9"
                                        className="text-center"
                                    >

                                        No Subscriptions Found

                                    </td>

                                </tr>

                                :

                                subscriptions.map(subscription => (

                                    <tr key={subscription.id}>


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                                        <td>

                                            {subscription.id}

                                        </td>

                                        <td>

                                            {subscription.userName}

                                        </td>

                                        <td>

                                            {subscription.planName}

                                        </td>

                                        <td>

                                            â‚¹ {subscription.price}

                                        </td>

                                        <td>

                                            {subscription.startDate}

                                        </td>

                                        <td>

                                            {subscription.endDate}

                                        </td>

                                        <td>

                                            {

                                                subscription.active ?

                                                    <span className="badge bg-success">

                                                        Active

                                                    </span>

                                                    :

                                                    <span className="badge bg-danger">

                                                        Expired

                                                    </span>

                                            }

                                        </td>

                                        <td>

                                            {

                                                subscription.paymentStatus ?

                                                    <span className="badge bg-primary">

                                                        Paid

                                                    </span>

                                                    :

                                                    <span className="badge bg-warning text-dark">

                                                        Pending

                                                    </span>

                                            }

                                        </td>

                                        <td>

                                            {

                                                subscription.active ?

                                                    <button

                                                        className="btn btn-warning btn-sm me-2"

                                                        onClick={() =>

                                                            deactivateSubscription(subscription.id)

                                                        }

                                                    >

                                                        Deactivate

                                                    </button>

                                                    :

                                                    <button

                                                        className="btn btn-success btn-sm me-2"

                                                        onClick={() =>

                                                            activateSubscription(subscription.id)

                                                        }

                                                    >

                                                        Activate

                                                    </button>

                                            }

                                            <button

                                                className="btn btn-danger btn-sm"

                                                onClick={() =>

                                                    deleteSubscription(subscription.id)

                                                }

                                            >

                                                Delete

                                            </button>

                                        </td>

                                    </tr>

                                ))

                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

};

export default ManageSubscriptions;
