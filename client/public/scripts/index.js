const loginScreen = document.createElement("login-screen")
const signInScreen = document.createElement("sign-in-screen")
const tamagotchiScreen = document.createElement("tamagotchi-screen")

document.body.appendChild(loginScreen)

loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", () => {
    goToTamagotchiScreen(null)
})

signInScreen.addEventListener("tamagotchiSubmited", async (ev) => {
    const response = await createTamagotchi(ev.detail.username, ev.detail.tamagotchiName)

    if (response) {
        console.log(response)
    } else {
        window.alert("Falha ao criar o tamagotchi")
    }
})

let tamagotchi = null

//
//AUX FUNCTIONS
function goToTamagotchiScreen(tamagotchiData) {
    tamagotchi = tamagotchiData
    document.body.replaceChildren(tamagotchiScreen)
}

function goToSignInScreen() {
    document.body.replaceChildren(signInScreen)
}

function goToLoginFromSignInScreen() {
    document.body.replaceChildren(loginScreen)
}