const express = require("express")
const net = require("net");

const router = express.Router();

router.post('/write', (req, res) => {
    const tcpClient = new net.Socket();

    tcpClient.connect(12345, '127.0.0.1', () => {
        tcpClient.write(req.body.data)
        tcpClient.on('data', data => {
            const response = JSON.parse(data.toString())
            res.send(response)
        })
    });
});

module.exports = router