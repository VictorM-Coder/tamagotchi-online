const net = require("net");
const events = require('events');
const eventEmitter = new events.EventEmitter();

class TamagotchiRepository {
    constructor() {
        this.tcpClient = new net.Socket()
        this.tcpClient.connect(12345, '127.0.0.1', () => {
            console.log('Connected to the server');
        })

        eventEmitter.on('sendRequest', (request) => {
            this.tcpClient.write(request);
            console.log(`Request sent: ${request}`);
        });

        this.tcpClient.on('close', () => {
            console.log('Connection closed');
        });

        this.tcpClient.on('error', (err) => {
            console.error('Connection error:', err);
        });
    }

    async communicateWithServer(request) {
        return new Promise((resolve) => {
            eventEmitter.emit('sendRequest', request);

            this.tcpClient.once('data', data => {
                const response = JSON.parse(data.toString());
                resolve(response);
            });
        });
    }
}

module.exports = TamagotchiRepository