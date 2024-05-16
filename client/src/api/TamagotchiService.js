const net = require("net");

class TamagotchiService {
    async createTamagotchi(username, tamagotchiName)  {
        const request = this.buildRequest(username, "NAME", tamagotchiName)
        return await this.communicateWithServer(request)
    }

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

    buildRequest(name, action, body) {
        if (body){
            return `${name} ${action} ${body}\n`
        } else {
            return `${name} ${action}\n`
        }
    }
}



module.exports = TamagotchiService