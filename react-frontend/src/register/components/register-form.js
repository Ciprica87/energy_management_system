import React from 'react';
import validate from "./validators/register-validators";
import Button from "react-bootstrap/Button";
import * as API_REGISTER from "../api/register-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import { Col, Row, FormGroup, Input, Label } from 'reactstrap';
import { useNavigate } from 'react-router-dom';
import { Redirect } from 'react-router-dom';

const localStorage = window.localStorage;

class RegisterForm extends React.Component {
    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);

        this.state = {
            errorStatus: 0,
            error: null,
            isUserLoaded: false,
            formIsValid: false,
            formControls: {
                userName: {
                    value: '',
                    placeholder: 'Username...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                password: {
                    value: '',
                    placeholder: 'Password...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 4,
                        isRequired: true
                    }
                },
                role: {
                    value: '',
                    valid: false,
                    touched: false,
                    validationRules: {
                        isRequired: true
                    }
                }
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }

    handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;
        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });
    };

    registerUser(user) {
        return API_REGISTER.postRegister(user, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully authenticated user with token: " + result.token);
    
                localStorage.setItem("token", result.token); // Store the JWT token
                localStorage.setItem("username", result.user.name); // Store username
                localStorage.setItem("role", result.user.role); // Store user role
                this.setState({
                    isUserLoaded: true
                });
            } else {
                this.setState({
                    errorStatus: status,
                    error: error
                });
            }
        });
    }

    handleSubmit() {
        let user = {
            name : this.state.formControls.userName.value,
            password: this.state.formControls.password.value,
            role: this.state.formControls.role.value,
        };

        console.log(user);
        this.registerUser(user);
    }


    render() {
        return (
            <div>
                 {this.state.isUserLoaded && localStorage.role === "ROLE_ADMIN" && <Redirect to="/admin" push/>}
                {this.state.isUserLoaded && localStorage.role === "ROLE_CLIENT" && <Redirect to="/user" push/>}


                <FormGroup id='userName'>
                    <Label for='userNameField'> Username: </Label>
                    <Input name='userName' id='userNameField' placeholder={this.state.formControls.userName.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.userName.value}
                           touched={this.state.formControls.userName.touched? 1 : 0}
                           valid={this.state.formControls.userName.valid}
                           required
                    />
                    {this.state.formControls.userName.touched && !this.state.formControls.userName.valid &&
                    <div className={"error-message row"}> * Username must have at least 3 characters </div>}
                </FormGroup>

                <FormGroup id='password'>
                    <Label for='passwordField'> Password: </Label>
                    <Input name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                           type='password'
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.password.value}
                           touched={this.state.formControls.password.touched? 1 : 0}
                           valid={this.state.formControls.password.valid}
                           required
                    />
                    {this.state.formControls.password.touched && !this.state.formControls.password.valid &&
                    <div className={"error-message"}> * Passowrd must be at least 4 characters long</div>}
                </FormGroup>

                {/* Role Field */}
                <FormGroup id='role'>
                    <Label for='roleField'> Role: </Label>
                    <Input type='select' name='role' id='roleField' onChange={this.handleChange}
                           defaultValue={this.state.formControls.role.value} required>
                        <option value="">Select a Role</option>
                        <option value="ROLE_CLIENT">Client</option>
                        <option value="ROLE_ADMIN">Admin</option>
                    </Input>
                </FormGroup>

                    <Row>
                        <Col sm={{size: '4', offset: 2}}>
                            <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>  Register </Button>
                        </Col>
                    </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        ) ;
        
    }
}

export default RegisterForm;