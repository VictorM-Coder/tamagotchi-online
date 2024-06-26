const TamagotchiRepository = require("./TamagotchiRepository")

class TamagotchiService {
    constructor() {
        this.repository = new TamagotchiRepository()
    }

    async connectToTamagotchi(username, id)  {
        const request = TamagotchiService.#buildRequest("CONNECT", username, id)
        return await this.repository.communicateWithServer(request)
    }

    async createTamagotchi(username, tamagotchiName)  {
        const request = TamagotchiService.#buildRequest("CREATE", username, tamagotchiName)
        return await this.repository.communicateWithServer(request)
    }

    async playWithTamagotchi()  {
        return await this.repository.communicateWithServer("PLAY\n")
    }

    async putTamagotchiToSleep()  {
        return await this.repository.communicateWithServer("SLEEP\n")
    }

    async feedTamagotchi()  {
        return await this.repository.communicateWithServer("EAT\n")
    }

    async awakeTamagotchi()  {
        return await this.repository.communicateWithServer("AWAKE\n")
    }

    async getTamagotchi()  {
        return await this.repository.communicateWithServer("GET\n")
    }

    async disconnectTamagotchi()  {
        return await this.repository.communicateWithServer("END\n")
    }

    static #buildRequest(action, owner, body) {
        return `${action} ${owner} ${body}\n`
    }
}

module.exports = TamagotchiService