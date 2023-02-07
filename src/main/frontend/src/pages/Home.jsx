import React, {useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../styles/homePage.css'
import {faPlus} from "@fortawesome/free-solid-svg-icons";
import {useHistory} from "react-router-dom";

/**
 * Home component is the main page of the app. It displays the file upload form and
 * handles the file upload process.
 *
 * @returns {JSX.Element} A react component that represents the home page of the app.
 */
const Home = () => {

    const history = useHistory();
    const [displayInformation, setDisplayInformation] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [firstName] = useState(sessionStorage.getItem("firstName"));

    /**
     * Handles the file upload process and makes a POST request to the server.
     *
     * @param {object} event - A synthetic event object that represents the file input change event.
     */
    const uploadFile = (event) => {
        const data = new FormData();
        data.append('file', event.target.files[0]);
        data.append('username', firstName)

        const requestOptions = {
            method: 'POST',
            body: data
        }

        fetch('/api/upload', requestOptions)
            .then((response) => {

                switch (response.status) {
                    case 403:
                        setDisplayInformation(true);
                        throw new Error("The .xlsx file is not correctly formatted.");
                    case 404:
                        throw new Error(`User ${firstName} not found`);
                    case 400:
                        throw new Error(`Unsupported file type`);
                    case 500:
                        throw new Error(`Server error`);
                    default:
                        return "success";
                }
            })
            .then(() => {
                setIsSuccessful(true);
            })
            .catch((error) => {
                setIsSuccessful(false);
                setErrorMessage(error.message);
            });
    }

    /**
     * Redirects the user to the graph page if the file upload is successful.
     */
    useEffect(() => {
        if (isSuccessful) {
            history.push('/graph');
        }

    }, [isSuccessful, history]);

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

                    {errorMessage !== "" && <div className="error-message">{errorMessage}</div>}
                    {displayInformation === true &&
                        <div className='example-image'>
                            <p className="image-description">Example of Excel file:</p>
                            <img className='info-img' src={process.env.PUBLIC_URL + '/Excel.png'} alt="example of excel"/>
                        </div>
                    }
                </div>
            </div>

        </>
    );
}

export default Home;