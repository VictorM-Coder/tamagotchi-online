function createTamagotchi(who, name) {
    fetch("/api/write", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ data: `${who} NAME ${name}\n` })
    })
        .then(response => response.text())
        .then(data => {
            return data
        })
        .catch(error => {
            console.error('Erro ao receber dados:', error);
        });
}