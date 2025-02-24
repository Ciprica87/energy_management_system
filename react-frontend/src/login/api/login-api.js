import { HOST } from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

const endpoint = {
    login: '/login'
};

function postLogin(user, callback) {
    let request = new Request(HOST.users_backend_api + endpoint.login, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    RestApiClient.performRequest(request, (data, status, error) => {
        if (status === 200) {
            // Successful response (status 200 OK)
            callback(data, status, null);
        } else {
            // Handle errors or unexpected status codes
            let errorMessage = error ? error.message : 'Unknown error';
            callback(null, status, errorMessage);
        }
    });
}

export {
    postLogin
};
