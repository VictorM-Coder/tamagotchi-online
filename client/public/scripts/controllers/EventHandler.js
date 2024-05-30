const loginScreen = document.createElement("login-screen")
const signInScreen = document.createElement("sign-in-screen")
const tamagotchiScreen = document.createElement("tamagotchi-screen")
const tamagotchiService = new TamagotchiService()

document.body.appendChild(loginScreen)

//Handle LoginScreen events
loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", async (ev) => {
    const username = ev.detail.username
    const id = ev.detail.id

    const response = await tamagotchiService.getTamagotchi(username, id)
    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
        LocalstorageUtils.setUserAndId(username, id)
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to connect to the Tamagotchi")
    }
})

//Handle SignInScreen events
signInScreen.addEventListener("tamagotchiSubmited", async (ev) => {
    const response = await tamagotchiService.createTamagotchi(ev.detail.username, ev.detail.tamagotchiName)
    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
        LocalstorageUtils.setUserAndId(ev.detail.username, response.tamagotchi.id)
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to create the Tamagotchi")
    }
})

//Handle TamagotchiScreen events
tamagotchiScreen.addEventListener("feedTamagotchi", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.feedTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
    } else {
        window.alert("Failed to feed the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("putTamagochiToSleepEvent", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.putTamagotchiToSleep(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
    } else {
        window.alert("Failed to put the Tamagotchi to sleep")
    }
})

tamagotchiScreen.addEventListener("awakeTamagotchiEvent", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.awakeTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
    } else {
        window.alert("Failed awakening Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("playWithTamagotchi", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.playWithTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
    } else {
        window.alert("Failed to play with the Tamagotchi")
    }
})
