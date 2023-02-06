import React, {useEffect, useState} from 'react';
import {useHistory} from "react-router-dom";

import '../styles/loginPage.css'


const CreateUser = () => {

    const history = useHistory();
    const [isSuccessful, setIsSuccessful] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");

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

        fetch("http://localhost:8080/api/addUser", options).then(response => {

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