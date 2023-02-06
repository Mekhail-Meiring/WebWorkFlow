import React, {useEffect, useState} from 'react';
import {useHistory} from "react-router-dom";

import '../styles/createUserPage.css'

/**
 * A React component that allows the user to create a user by entering their first name,
 * last name, and date of birth. The entered information is stored in session storage
 * and sent to the server via a POST request to "/api/addUser".
 */
const CreateUser = () => {

    const history = useHistory();
    const [isSuccessful, setIsSuccessful] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");

    /**
     * Handles the submission of the create user form. Sends a POST request to the server
     * with the entered user information.
     *
     * @param {Object} event - The submit event of the form.
     */
    const onSubmit = (event) => {

        event.preventDefault();

        const data = new FormData(event.target);
        const firstName = data.get('first_name');
        const lastName = data.get('last_name');
        const dateOfBirth = data.get('date_of_birth');

        sessionStorage.setItem("firstName", firstName.toString());
        sessionStorage.setItem("lastName", lastName.toString());
        sessionStorage.setItem("dateOfBirth", dateOfBirth.toString());

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                first_name : firstName,
                last_name : lastName,
                date_of_birth : dateOfBirth
            })
        }

        fetch("/api/addUser", options).then(response => {

            if (response.ok) {return response.json();}
            if (response.status === 400) {throw new Error("Invalid user credentials");}

            throw new Error("Something went wrong");})

            .then(data => {
                console.log(data);
                setIsSuccessful(true);
            })
            
            .catch(error => {
                console.log(error);
                setIsSuccessful(false);
                setErrorMessage(error.message);
            });
    }

    useEffect(() => {
        if (isSuccessful) {
            history.push('/home');
        }
        else {
            history.push('/');
        }
    }, [isSuccessful, history]);

    
    return (
        <div className="cover">
            <h1>Create User</h1>
            <form className='create-user-form' onSubmit={onSubmit}>
                <input className='create-user-field' name='first_name' type="text" placeholder="First name"/>
                <input className='create-user-field' name='last_name' type="text" placeholder="Last name"/>
                <input className='create-user-field' name='date_of_birth' type="date" placeholder="Date of birth"/>
                <input className='create-user-btn' type='submit' value='Create'/>
            </form>
            {errorMessage !== "" && <div className="error-message">{errorMessage}</div>}
        </div>
        
    );
}

export default CreateUser;