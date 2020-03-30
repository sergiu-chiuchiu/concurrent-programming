// API logic
const url = require('url');
const employees = require('./employeeData.js');
const helpers = require('./Helper.js');
const constants = require('./Constants.js');


exports.getEmployees = function (req, res) {
    const response = helpers.response("This is the list of employees", employees)
    helpers.sendResponse(res, 200, response)
}

exports.getEmployeeById = function (req, res) {
    const employeeId = helpers.getEmployeeId(req);

    // check if id is number
    if (isNaN(employeeId)) {
        helpers.invalidId(req, res);
        return;
    }
    // check if id exists
    const employee = employees.find(empl => {
        return empl.id == employeeId;
    })
    if (!employee) {
        helpers.employeeIdDoesNotExists(req, res)
        return;
    }

    const response = helpers.response("This is the requested employee", employee)
    helpers.sendResponse(res, 200, response)
}

exports.createEmployee = function (req, res) {
    body = '';
    // reading data from stream
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        const reqBody = JSON.parse(body);
        const response = helpers.response("Employee added successfully")

        if (employees.find(empl => empl.id === reqBody.id)) {
            response[0].message = "Employee already exists"
            helpers.sendResponse(res, 400, response)
            return;
        }

        if (!reqBody.id || !reqBody.firstName || !reqBody.lastName || !reqBody.department) {
            response[0].message = "Not all required fields are present"
            helpers.sendResponse(res, 400, response) 
            return;
        }


        employees.push(reqBody)
        response.push(reqBody)
        helpers.sendResponse(res, 201, response) 
    })
}

exports.createRoleInEmployee = function (req, res) {
    const reqUrl = url.parse(req.url, true);
    const employeeId = reqUrl.pathname.split("/")[2]

    // check if id is number
    if (isNaN(employeeId)) {
        helpers.invalidId(req, res)
        return;
    }
    // check if id exists
    const employee = employees.find(empl => {
        return empl.id == employeeId;
    })
    if (!employee) {
        helpers.employeeIdDoesNotExists(req, res)
        return;
    }

    body = '';
    // reading data from stream
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        const reqBody = JSON.parse(body);
        const employee = employees.find(empl => {
            let isIdMatched;
            if (isIdMatched = (empl.id == employeeId)) {
                empl.responsibilities.push(reqBody.responsibility)
            }
            return isIdMatched;
        })
        
        const response = helpers.response("The new role has been sucessfully added", employee)
        helpers.sendResponse(res, 201, response)
    

        // var response = [
        //     {
        //         "message": "The new role has been sucessfully added"
        //     },
        //     employee
        // ];
        // res.statusCode = 201;
        // res.setHeader('content-Type', 'Application/json');
        // res.end(JSON.stringify(response))
    })
}

exports.putEmployee = function (req, res) {
    body = '';
    // reading data from stream
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        const reqBody = JSON.parse(body);

        var response = [
            {
                "message": "Employee created successfully"
            }
        ]

        if (!reqBody.id || !(reqBody.firstName || reqBody.lastName || reqBody.department || reqBody.responsibilities)) {
            res.statusCode = 400;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "No new value provided"))
            return;
        }

        if (!reqBody.id || !reqBody.firstName || !reqBody.lastName || !reqBody.department) {
            res.statusCode = 400;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "Not all required fields are present"))
            return;
        }

        const foundEmployee = employees.find((empl, idx) => {
            let isIdMatched;
            if (isIdMatched = (empl.id == reqBody.id)) {
                employees[idx] = {}
                employees[idx] = { reqBody }
            }
            return isIdMatched;
        })

        if (foundEmployee) {
            response.push(reqBody)
            res.statusCode = 201;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "Employee modified"))
            return;
        }

        employees.push(reqBody)
        response.push(reqBody)
        res.statusCode = 201;
        res.setHeader('content-Type', 'Application/json');
        res.end(JSON.stringify(response))
    })
}

exports.putEmployeeById = function (req, res) {
    const reqUrl = url.parse(req.url, true);
    const employeeId = reqUrl.pathname.split("/")[2]

    // check if id is number
    if (isNaN(employeeId)) {
        helpers.invalidId(req, res)
        return;
    }

    body = '';
    // reading data from stream
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        let reqBody = JSON.parse(body);

        var response = [
            {
                "message": "Employee modified successfully"
            }
        ]

        if (!reqBody.id) {
            reqBody.id = employeeId
        }

        if (!reqBody.id || !(reqBody.firstName || reqBody.lastName || reqBody.department || reqBody.responsibilities)) {
            res.statusCode = 400;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "No new value provided"))
            return;
        }

        if (!reqBody.id || !reqBody.firstName || !reqBody.lastName || !reqBody.department) {
            res.statusCode = 400;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "Not all required fields are present"))
            return;
        }

        if (employeeId != reqBody.id) {
            res.statusCode = 400;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "Ids provided does not match"))
            return;
        }

        const emplIdx = employees.findIndex(empl => empl.id == employeeId)
        if (emplIdx !== -1) {
            employees[emplIdx] = reqBody
            response.push(reqBody)
            res.statusCode = 201;
            res.setHeader('content-Type', 'Application/json');
            res.end(JSON.stringify(response[0].message = "Employee modified"))
            return;
        }
  
        employees.push(reqBody)
        response.push(reqBody)
        res.statusCode = 201;
        res.setHeader('content-Type', 'Application/json');
        res.end(JSON.stringify(response))
    })
}

exports.deleteEmployees = function (req, res) {
    employees.length = 0
    res.statusCode = 204;
    res.end()
}

exports.deleteEmployeeById = function (req, res) {
    const reqUrl = url.parse(req.url, true);
    const employeeId = reqUrl.pathname.split("/")[2]
    const emplIdx = employees.findIndex(empl => employeeId == empl.id)
    if (emplIdx !== -1) {
        employees.splice(emplIdx, 1)
        res.statusCode = 204;
        res.end()
    }
}
