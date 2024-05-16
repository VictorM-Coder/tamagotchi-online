const net = require("net");

class TamagotchiRepository {
    async communicateWithServer(request) {
        return new Promise(resolve => {
            const tcpClient = new net.Socket();

            tcpClient.connect(12345, '127.0.0.1', () => {
                tcpClient.write(request)

                tcpClient.on('data', data => {
                    const response = JSON.parse(data.toString())
                    resolve(response)
                })
            });
        })
    }
}

module.exports = TamagotchiRepository