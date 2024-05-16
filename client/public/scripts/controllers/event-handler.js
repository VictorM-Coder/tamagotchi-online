const loginScreen = document.createElement("login-screen")
const signInScreen = document.createElement("sign-in-screen")
const tamagotchiScreen = document.createElement("tamagotchi-screen")

document.body.appendChild(loginScreen)

loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", () => {
    goToTamagotchiScreen()
})

signInScreen.addEventListener("tamagotchiSubmited", async (ev) => {
    const response = await createTamagotchi(ev.detail.username, ev.detail.tamagotchiName)

    if (response) {
        //todo salvar os valores da resposta
        console.log(response)
    } else {
        window.alert("Falha ao criar o tamagotchi")
    }
})
