function createTamagotchi(who, name) {
    return fetch("/api/create", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: who, tamagotchiName: name })
    })
        .then(response => response.text())
        .then(data => {
            return data
        })
        .catch(error => {
            console.error('Erro ao receber dados:', error);
        });
}