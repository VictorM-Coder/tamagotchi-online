const express = require("express")
const net = require('net');

const app = express()

app.use(express.static("public"))


// app.get('/data', (req, res) => {
//     const tcpClient = new net.Socket();
//     tcpClient.connect(12345, '127.0.0.1', () => {
//         console.log('Conectado ao servidor TCP!');
//     });
// });

app.listen(3000, () => {
    console.log("Site available in http://localhost:3000")
    const tcpClient = new net.Socket();
    tcpClient.connect(12345, '127.0.0.1', () => {
        tcpClient.write("Victor NAME vlad\n")
    });
})