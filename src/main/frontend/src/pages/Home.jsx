import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../styles/homePage.css'
import {faPlus} from "@fortawesome/free-solid-svg-icons";

const Home = () => {

    const uploadFile = (event) => {

        const data = new FormData(event.target)
        data.get("file")

    }

    const [firstName, setFirstName] = useState(sessionStorage.getItem("firstName"));

    return (
        <>
            <div className="file-upload-container">
                <h1 className='greeting'>Welcome {firstName}!</h1>
                <p className='upload-message'>Upload your monthly income and expenses Excel file :</p>

                <div className="file-card">
                    <div className="file-input">
                        <input className='file-input-field' type="file" onChange={uploadFile} />
                        <button className='file-btn'>
                            <i className='file-icon'>
                                <FontAwesomeIcon icon={faPlus} />
                            </i>
                            Upload
                        </button>
                    </div>

                    <p className="main">Supported file types:</p>
                    <p className="info">Excel (.xlsx)</p>
                </div>

            </div>

        </>
    );
}

export default Home;