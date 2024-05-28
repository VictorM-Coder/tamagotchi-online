const TamagotchiRepository = require("./TamagotchiRepository")

class TamagotchiService {
    repository = new TamagotchiRepository()
    async createTamagotchi(username, tamagotchiName)  {
        const request = TamagotchiService.#buildRequest(username, "NAME", tamagotchiName)
        return await this.repository.communicateWithServer(request)
    }

    async playWithTamagotchi(username, id)  {
        const request = TamagotchiService.#buildRequest(username, "PLAY", id)
        return await this.repository.communicateWithServer(request)
    }

    async putTamagotchiToSleep(username, id)  {
        const request = TamagotchiService.#buildRequest(username, "SLEEP", id)
        return await this.repository.communicateWithServer(request)
    }

    async feedTamagotchi(username, id)  {
        const request = TamagotchiService.#buildRequest(username, "EAT", id)
        return await this.repository.communicateWithServer(request)
    }

    async awakeTamagotchi(username, id)  {
        const request = TamagotchiService.#buildRequest(username, "AWAKE", id)
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