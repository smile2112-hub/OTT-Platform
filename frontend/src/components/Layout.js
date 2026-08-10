import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

function Layout() {
    return (
        <>
            <Navbar />


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

            <div
                style={{
                    minHeight: "calc(100vh - 70px)",
                    background: "#141414",
                    color: "#fff"
                }}
            >
                <Outlet />
            </div>
        </>
    );
}

export default Layout;
