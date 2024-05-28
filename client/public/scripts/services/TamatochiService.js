class TamagotchiService {
    async createTamagotchi(username, tamagotchiName) {
        return this.#sendRequest("/api/create", "POST", { username, tamagotchiName });
    }

    async feedTamagotchi(username, id) {
        return this.#sendRequest("/api/eat", "PUT", { username, id });
    }

    async putTamagotchiToSleep(username, id) {
        return this.#sendRequest("/api/sleep", "PUT", { username, id });
    }

    async playWithTamagotchi(username, id) {
        return this.#sendRequest("/api/play", "PUT", { username, id });
    }

    async awakeTamagotchi(username, id) {
        return this.#sendRequest("/api/awake", "PUT", { username, id });
    }

    async #sendRequest(url, method, body) {
        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            });

            const data = await response.text();
            return this.#processData(data);
        } catch (error) {
            console.error(`Error during ${method} request to ${url}:`, error);
            return null;
        }
    }

    #processData(data) {
        try {
            const response = JSON.parse(data);
            if (response.status === "SUCCESS") {
                return response.body;
            } else {
                console.error('Error in response data:', response);
                return null;
            }
        } catch (error) {
            console.error('Error parsing response data:', error);
            return null;
        }
    }
}
