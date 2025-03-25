import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    users: '/getUsers',
    user: '/getUser',
    basic: ''
};

function getUsers(callback) {
    let request = new Request(HOST.users_backend_api + endpoint.users, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getUserById(id, callback){
    let request = new Request(HOST.users_backend_api + endpoint.user + '/'+ id, {
       method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function updateUser(user, callback){
    const updateUrl = '/updateUser' + '/' + user.id 
    let request = new Request(HOST.users_backend_api + endpoint.basic + updateUrl, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function insertUser(user, callback){
    const insertUrl = '/addUserGlobal'
    let request = new Request(HOST.users_backend_api + endpoint.basic + insertUrl, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteUser(id, callback){
    const deleteUrl = '/deleteUser/' + id
    let request = new Request(HOST.users_backend_api + endpoint.basic + deleteUrl, {
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
    getUsers,
    updateUser,
    getUserById,
    insertUser,
    deleteUser
};
