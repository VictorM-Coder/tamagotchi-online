const loginScreen = document.createElement("login-screen")
const signInScreen = document.createElement("sign-in-screen")
const tamagotchiScreen = document.createElement("tamagotchi-screen")
const tamagotchiService = new TamagotchiService()

document.body.appendChild(loginScreen)

loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", () => {
    goToTamagotchiScreen()
})

signInScreen.addEventListener("tamagotchiSubmited", async (ev) => {
    const response = await tamagotchiService.createTamagotchi(ev.detail.username, ev.detail.tamagotchiName)
    if (response) {
        tamagotchiScreen.tamagotchi = response
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to create the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("feedTamagotchi", async () => {
    const response = await tamagotchiService.feedTamagotchi("jose")
    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to feed the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("putTamagochiToSleepEvent", async () => {
    const response = await tamagotchiService.putTamagotchiToSleep("jose")
    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to put the Tamagotchi to sleep")
    }
})

tamagotchiScreen.addEventListener("playWithTamagotchi", async () => {
    const response = await tamagotchiService.playWithTamagotchi("jose")
    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to play with the Tamagotchi")
    }
})
