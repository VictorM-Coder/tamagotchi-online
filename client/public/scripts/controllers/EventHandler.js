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
        LocalstorageUtils.setUserAndId(ev.detail.username, response.id)
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to create the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("feedTamagotchi", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.feedTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to feed the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("putTamagochiToSleepEvent", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.putTamagotchiToSleep(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to put the Tamagotchi to sleep")
    }
})

tamagotchiScreen.addEventListener("awakeTamagotchiEvent", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.awakeTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed awakening Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("playWithTamagotchi", async () => {
    const user = LocalstorageUtils.getUser()
    const response = await tamagotchiService.playWithTamagotchi(user.username, user.id)

    if (response) {
        tamagotchiScreen.tamagotchi = response
    } else {
        window.alert("Failed to play with the Tamagotchi")
    }
})
