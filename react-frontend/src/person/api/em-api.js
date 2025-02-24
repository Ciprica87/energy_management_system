import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    em: ''
};

function getEnergyMeters(callback) {
    const url = '/getDevices'
    let request = new Request(HOST.em_backend_api + endpoint.em + url, {
        method: 'GET',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function insertEnergyMeter(energyMeter, callback){
    const url = '/insertDevice'
    let request = new Request(HOST.em_backend_api + endpoint.em + url, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(energyMeter)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updateEnergyMeter(energyMeter, callback){
    const url = '/updateDevice'
    let request = new Request(HOST.em_backend_api + endpoint.em + url + '/' + energyMeter.id, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(energyMeter)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteEnergyMeter(id, callback){
    const url = '/deleteDevice'
    let request = new Request(HOST.em_backend_api + endpoint.em + url + '/' + id, {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(id)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getEnergyMeters,
    insertEnergyMeter,
    updateEnergyMeter,
    deleteEnergyMeter
};
