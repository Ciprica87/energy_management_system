import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

const endpoint = {
    register: '/register' // Change the endpoint to include '/register'
};

function postRegister(user, callback) {
    let request = new Request(HOST.users_backend_api + endpoint.register, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    RestApiClient.performRequest(request, (data, status, error) => {
        if (status === 200 || status === 201) {
            // Successful response (status 200 OK or 201 Created)
            callback(data, status, null);
        } else {
            // Handle errors or unexpected status codes
            let errorMessage = error ? error.message : 'Unknown error';
            callback(null, status, errorMessage);
        }
    });
}

export {
    postRegister
};