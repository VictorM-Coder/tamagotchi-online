const TamagotchiRepository = require("./TamagotchiRepository")

class TamagotchiService {
    repository = new TamagotchiRepository()
    async createTamagotchi(username, tamagotchiName)  {
        const request = TamagotchiService.#buildRequest(username, "NAME", tamagotchiName)
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