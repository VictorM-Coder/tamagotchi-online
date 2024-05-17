const TamagotchiRepository = require("./TamagotchiRepository")

class TamagotchiService {
    repository = new TamagotchiRepository()
    async createTamagotchi(username, tamagotchiName)  {
        const request = TamagotchiService.#buildRequest(username, "NAME", tamagotchiName)
        return await this.repository.communicateWithServer(request)
    }

    async playWithTamagotchi(username)  {
        const request = TamagotchiService.#buildRequest(username, "PLAY")
        return await this.repository.communicateWithServer(request)
    }

    async putTamagotchiToSleep(username)  {
        const request = TamagotchiService.#buildRequest(username, "SLEEP")
        return await this.repository.communicateWithServer(request)
    }

    async feedTamagotchi(username)  {
        const request = TamagotchiService.#buildRequest(username, "EAT")
        return await this.repository.communicateWithServer(request)
    }

    static #buildRequest(name, action, body) {
        if (body){
            return `${name} ${action} ${body}\n`
        } else {
            return `${name} ${action}\n`
        }
    }
}



module.exports = TamagotchiService