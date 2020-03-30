const http = require('http');
const url = require('url');
const helpers = require('./Helper.js')

module.exports = http.createServer((req, res) => {
    var employeeController = require('./EmployeeController.js');
    helpers.log(req);
    const reqUrl = url.parse(req.url, true);
    if (reqUrl.pathname == '/employee' && req.method === 'GET') {
        employeeController.getEmployees(req, res);

    } else if (reqUrl.pathname.split("/")[1] == 'employee'
        && reqUrl.pathname.split("/")[2]
        && req.method === 'GET') {
        employeeController.getEmployeeById(req, res)

    } else if (reqUrl.pathname == '/employee' && req.method === 'POST') {
        employeeController.createEmployee(req, res);

    } else if (reqUrl.pathname.split("/")[1] == 'employee'
        && reqUrl.pathname.split("/")[2]
        && req.method === 'POST') {
        employeeController.createRoleInEmployee(req, res);

    } else if (reqUrl.pathname == '/employee' && req.method === 'PUT') {
        employeeController.putEmployee(req, res);

    } else if (reqUrl.pathname.split("/")[1] == 'employee'
        && reqUrl.pathname.split("/")[2]
        && req.method === 'PUT') {
        employeeController.putEmployeeById(req, res);

    } else if (reqUrl.pathname == '/employee' && req.method === 'DELETE') {
        employeeController.deleteEmployees(req, res);

    } else if (reqUrl.pathname.split("/")[1] == 'employee'
        && reqUrl.pathname.split("/")[2]
        && req.method === 'DELETE') {
        employeeController.deleteEmployeeById(req, res);

    } else {
        helpers.invalidUrl(req, res);
    }
})