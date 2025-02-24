import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    em: '/getDevices'
};

function getEnergyMeters(callback) {
    let request = new Request(HOST.em_backend_api + endpoint.em, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getEnergyMeters
};
