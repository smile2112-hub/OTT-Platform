import React, { useEffect, useState } from "react";
import UserService from "../services/UserService";

const Profile = () => {

    const [profile, setProfile] = useState({

        nameSurname: "",
        username: "",
        email: "",
        profilePicture: ""

    });

    const [oldPassword, setOldPassword] = useState("");

    const [newPassword, setNewPassword] = useState("");

    useEffect(() => {

        loadProfile();

    }, []);

    const loadProfile = async () => {

        try {

            const response =
                await UserService.getMyProfile();

            setProfile(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const updateProfile = async (e) => {

        e.preventDefault();

        try {

            await UserService.updateProfile(profile);

            alert("Profile Updated Successfully");

            loadProfile();

        }

        catch (error) {

            alert("Unable To Update Profile");

        }

    };

    const changePassword = async (e) => {

        e.preventDefault();

        try {

            await UserService.changePassword(

                oldPassword,

                newPassword

            );

            alert("Password Changed Successfully");

            setOldPassword("");

            setNewPassword("");

        }

        catch (error) {

            alert("Unable To Change Password");

        }

    };

    const removeProfilePicture = async () => {

        try {

            await UserService.removeProfilePicture();

            loadProfile();

        }

        catch (error) {

            console.log(error);

        }

    };

    const deleteAccount = async () => {

        if (!window.confirm("Delete Your Account?")) {

            return;

        }

        try {

            await UserService.deleteMyAccount();

            localStorage.clear();

            window.location.href = "/login";

        }

        catch (error) {

            console.log(error);

        }

    };

    return (

        <div className="container mt-5">

            <div className="row">

                <div className="col-lg-4">

                    <div className="card shadow">

                        <div className="card-body text-center">

                            {

                                profile.profilePicture ?

                                <img

                                    src={profile.profilePicture}

                                    alt="Profile"

                                    className="rounded-circle"

                                    width="180"

                                    height="180"

                                />

                                :

                                <img

                                    src="https://cdn-icons-png.flaticon.com/512/149/149071.png"

                                    alt="Profile"

                                    className="rounded-circle"

                                    width="180"

                                    height="180"

                                />

                            }

                            <h3 className="mt-3">

                                {profile.nameSurname}

                            </h3>

                            <p>

                                {profile.username}

                            </p>

                            <button

                                className="btn btn-outline-danger"

                                onClick={removeProfilePicture}

                            >

                                Remove Picture

                            </button>

                        </div>

                    </div>

                </div>

                <div className="col-lg-8">

                    <div className="card shadow mb-4">

                        <div className="card-header">


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                            <h4>

                                Update Profile

                            </h4>

                        </div>

                        <div className="card-body">

                            <form onSubmit={updateProfile}>

                                <div className="mb-3">

                                    <label>

                                        Name

                                    </label>

                                    <input

                                        type="text"

                                        className="form-control"

                                        value={profile.nameSurname}

                                        onChange={(e) =>

                                            setProfile({

                                                ...profile,

                                                nameSurname:

                                                e.target.value

                                            })

                                        }

                                    />

                                </div>

                                <div className="mb-3">

                                    <label>

                                        Email

                                    </label>

                                    <input

                                        type="email"

                                        className="form-control"

                                        value={profile.email}

                                        onChange={(e) =>

                                            setProfile({

                                                ...profile,

                                                email:

                                                e.target.value

                                            })

                                        }

                                    />

                                </div>

                                <button

                                    className="btn btn-primary"

                                >

                                    Update Profile

                                </button>

                            </form>

                        </div>

                    </div>

                    <div className="card shadow mb-4">

                        <div className="card-header">

                            <h4>

                                Change Password

                            </h4>

                        </div>

                        <div className="card-body">

                            <form onSubmit={changePassword}>

                                <div className="mb-3">

                                    <label>

                                        Old Password

                                    </label>

                                    <input

                                        type="password"

                                        className="form-control"

                                        value={oldPassword}

                                        onChange={(e) =>

                                            setOldPassword(

                                                e.target.value

                                            )

                                        }

                                    />

                                </div>

                                <div className="mb-3">

                                    <label>

                                        New Password

                                    </label>

                                    <input

                                        type="password"

                                        className="form-control"

                                        value={newPassword}

                                        onChange={(e) =>

                                            setNewPassword(

                                                e.target.value

                                            )

                                        }

                                    />

                                </div>

                                <button

                                    className="btn btn-success"

                                >

                                    Change Password

                                </button>

                            </form>

                        </div>

                    </div>

                    <div className="card shadow">

                        <div className="card-header bg-danger text-white">

                            <h4>

                                Danger Zone

                            </h4>

                        </div>

                        <div className="card-body">

                            <button

                                className="btn btn-danger"

                                onClick={deleteAccount}

                            >

                                Delete My Account

                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

};

export default Profile;
