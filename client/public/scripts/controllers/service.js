function createTamagotchi(username, tamagotchiName) {
    return fetch(
        "/api/create",
        {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username, tamagotchiName: tamagotchiName})
        })
        .then(response => response.text())
        .then(data => {
            return JSON.parse(data)
        })
        .catch(error => {
            console.error('Error during creating tamagotchi:', error);
        });
}

function feedTamagotchi(username) {
    return fetch(
        "/api/eat",
        {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username})
        })
        .then(response => response.text())
        .then(data => {
            return JSON.parse(data)
        })
        .catch(error => {
            console.error('Error during feeding:', error);
        });
}

function putTamagotchiToSleep(username) {
    return fetch(
        "/api/sleep",
        {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username})
        })
        .then(response => response.text())
        .then(data => {
            return JSON.parse(data)
        })
        .catch(error => {
            console.error('Error during putting tamagotchi to sleep:', error);
        });
}

function playWithTamagotchi(username) {
    return fetch(
        "/api/play",
        {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username})
        })
        .then(response => response.text())
        .then(data => {
            return JSON.parse(data)
        })
        .catch(error => {
            console.error('Error during putting tamagotchi to play:', error);
        });
}