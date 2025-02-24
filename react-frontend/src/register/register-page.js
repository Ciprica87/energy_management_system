import React from 'react';
import RegisterForm from "./components/register-form";

const localStorage = window.localStorage;
localStorage.setItem("role", "")

class RegisterPage extends React.Component {
    render() {
        return (
            <RegisterForm/>
        )
    }
}

export default RegisterPage;