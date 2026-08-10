import { useState } from "react";
import "./SearchBar.css";

function SearchBar({ onSearch }) {

    const [keyword, setKeyword] = useState("");

    const search = () => {

        onSearch(keyword);

    };

    return (

        <div className="container mt-4 mb-4">

            <div className="input-group">

                <input

                    className="form-control"


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                    placeholder="Search Movies..."

                    value={keyword}

                    onChange={(e) => setKeyword(e.target.value)}

                />

                <button

                    className="btn btn-danger"

                    onClick={search}

                >

                    Search

                </button>

            </div>

        </div>

    );

}

export default SearchBar;
