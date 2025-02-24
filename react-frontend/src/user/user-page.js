import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Card,
    CardHeader,
    Col,
    Row,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    UncontrolledDropdown
} from 'reactstrap';
import * as API_EM from "./api/em-api"
import EnergyMeterTable from './components/em-table';
import { Redirect } from 'react-router-dom';
import ChatComponent from './components/chat-management/chat-manangement-container';

const textStyle = {
    color: 'black',
    textDecoration: 'none'
};

const localStorage = window.localStorage;

class UserPage extends React.Component {
    constructor(props) {
        super(props);
        this.setSelectedEntity = this.setSelectedEntity.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selectedEntity: 'energyMeters',
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchEnergyMeters();
    }

    fetchEnergyMeters() {
        return API_EM.getEnergyMeters((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState({
                    errorStatus: status,
                    error: err
                });
            }
        });
    }
    
    reload() {
        this.setState({
            isLoaded: false
        });
        this.fetchEnergyMeters();
    }
    

    setSelectedEntity = (entity) => {
        this.setState({selectedEntity: entity});
    }

    render() {
        let content;
        switch (this.state.selectedEntity) {
            case 'energyMeters':
                content = this.state.isLoaded ? <EnergyMeterTable tableData={this.state.tableData}/> : null;
                break;
            case 'chat': // Add a new case for the chat
                content = <ChatComponent/>; // Render your ChatComponent here
                break;
            default:
                content = <EnergyMeterTable tableData={this.state.tableData}/>;
        }

        return (
            <div>
                {localStorage.role !== "ROLE_CLIENT" && <Redirect to="/login" push/>}

                <CardHeader>
                    <UncontrolledDropdown nav inNavbar>
                        <DropdownToggle nav caret style={textStyle}>
                            Options
                        </DropdownToggle>
                        <DropdownMenu right>
                            <DropdownItem onClick={() => this.setSelectedEntity('energyMeters')}>
                                Energy Meters
                            </DropdownItem>
                            <DropdownItem onClick={() => this.setSelectedEntity('chat')}>
                                Chat
                            </DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </CardHeader>

                <Card>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {content}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                                            errorStatus={this.state.errorStatus}
                                                            error={this.state.error}
                                                        />}
                        </Col>
                    </Row>
                </Card>
            </div>
        )
    }
}

export default UserPage;
