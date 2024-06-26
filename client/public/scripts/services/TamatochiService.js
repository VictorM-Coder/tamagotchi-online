class TamagotchiService {
    async connectToTamagotchi(username, id) {
        const params = new URLSearchParams({ username, id });
        return this.#sendRequest(`/api/connect?${params.toString()}`, "GET");
    }

    async createTamagotchi(username, tamagotchiName) {
        return this.#sendRequest("/api/create", "POST", { username, tamagotchiName });
    }

    async feedTamagotchi() {
        return this.#sendRequest("/api/eat", "PUT");
    }

    async putTamagotchiToSleep() {
        return this.#sendRequest("/api/sleep", "PUT");
    }

    async playWithTamagotchi() {
        return this.#sendRequest("/api/play", "PUT");
    }

    async awakeTamagotchi() {
        return this.#sendRequest("/api/awake", "PUT");
    }

    async getTamagotchi() {
        return this.#sendRequest('/api/get', "GET");
    }

    async disconnectTamagotchi() {
        return this.#sendRequest('/api/end', "GET");
    }

    async #sendRequest(url, method, body) {
        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: (method !== "GET") ? JSON.stringify(body): null
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
