import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';

import * as API_EM from "./api/em-api"
import EnergyMeterTable from './components/em-table';
import { Redirect } from 'react-router-dom';

const localStorage = window.localStorage;

class UserPage extends React.Component {

    constructor(props) {
        super(props);
        this.reload = this.reload.bind(this);
        this.state = {
            addFormSelected: false,
            updateFormSelected: false,
            deleteFormSelected: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchPersons();
    }

    fetchPersons() {
        return API_EM.getEnergyMeters((result, status, err) => {
            if (result !== null && status === 200) {
                console.log(result)
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        this.fetchPersons();
    }

    render() {
        return (
            <div>
                {localStorage.role !== "ROLE_CLIENT" && <Redirect to="/login" push/>}

                <CardHeader>
                    <strong> Energy Meters </strong>
                </CardHeader>
                <Card>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <EnergyMeterTable  tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                                            errorStatus={this.state.errorStatus}
                                                            error={this.state.error}
                                                        />   }
                        </Col>
                    </Row>
                </Card>
            </div>
        )

    }
}

export default UserPage;
