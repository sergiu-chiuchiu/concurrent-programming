const url = require('url');
const constants = require("./Constants.js")

exports.getEmployeeId = req => {
    const reqUrl = url.parse(req.url, true);
    return reqUrl.pathname.split("/")[2];
}

exports.log = req => {
    console.log('Request type: ' + req.method + ' Endpoint: ' + req.url);
}

exports.invalidUrl = function (req, res) {
    const response = exports.response("This Endpoint does not exists, here are the available endpoints", constants.availableEndpoints)
    exports.sendResponse(res, 404, response)
}

exports.invalidId = function (req, res) {
    const response = exports.response("The id of the employee must be a number, here are the available endpoints", constants.availableEndpoints)
    exports.sendResponse(res, 400, response)
}

exports.employeeIdDoesNotExists = function (req, res) {
    const response = exports.response("The id of the requested employee does not exists")
    exports.sendResponse(res, 404, response)
}

exports.response = (message, data) => {
    let resTemplate = [
        {
            "message": message
        },
    ];
    data != null ? resTemplate.push(data) : ""
    return resTemplate;
}

exports.sendResponse = (res, statusCode, payload) => {
    res.statusCode = statusCode;
    res.setHeader('content-Type', 'Application/json');
    res.end(JSON.stringify(payload))
}