const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;

const server = require('./routes.js');

server.listen(port, hostname, () => {
    console.log(`Server started at http://${hostname}:${port}/`);
});
