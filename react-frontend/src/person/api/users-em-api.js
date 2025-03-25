import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    em: ''
};

function getEmUsers(callback) {
    let request = new Request(HOST.em_backend_api + endpoint.em, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function mapUserToEm(pair, callback){
    const url = '/addUserToDevice'
    let request = new Request(HOST.em_backend_api + endpoint.em + url + '/' + pair.userId + '/' + pair.emId, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function unmapUserToEm(pair, callback){
    const url = '/deleteUserFromDevice'
    let request = new Request(HOST.em_backend_api + endpoint.em + url + '/' + pair.userId + '/' + pair.emId, {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },

    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updateEmUser(emUser, callback){
    let request = new Request(HOST.em_backend_api + endpoint.em + '/' + emUser.id, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(emUser)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteEmUser(id, callback){
    let request = new Request(HOST.em_backend_api + endpoint.em + '/' + id, {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(id)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getEmUsers,
    mapUserToEm,
    unmapUserToEm,
    updateEmUser,
    deleteEmUser
};
