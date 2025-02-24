function performRequest(request, callback){
    fetch(request)
        .then(function(response) {
            console.log(response.status);
            if (response.ok) {
                response.json()
                    .then(json => callback(json, response.status, null))
                    .catch(err => callback(null, response.status, err)); // Handle JSON parsing errors
            } else {
                response.json()
                    .then(err => callback(null, response.status, err))
                    .catch(err => callback(null, response.status, "Error parsing JSON")); // Handle JSON parsing errors for non-ok responses
            }
        })
        .catch(function (err) {
            //catch any other unexpected error, and set custom code for error = 1
            callback(null, 1, err);
        });
}

module.exports = {
    performRequest
};
