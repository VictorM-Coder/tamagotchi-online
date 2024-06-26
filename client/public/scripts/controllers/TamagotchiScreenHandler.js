tamagotchiScreen.addEventListener("feedTamagotchi", async () => {
    const response = await tamagotchiService.feedTamagotchi()

    if (response) {
        handleResponse(response);
    } else {
        window.alert("Failed to feed the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("putTamagochiToSleepEvent", async () => {
    const response = await tamagotchiService.putTamagotchiToSleep()

    if (response) {
        handleResponse(response)
    } else {
        window.alert("Failed to put the Tamagotchi to sleep")
    }
})

tamagotchiScreen.addEventListener("awakeTamagotchiEvent", async () => {
    const response = await tamagotchiService.awakeTamagotchi()

    if (response) {
        handleResponse(response)
    } else {
        window.alert("Failed awakening Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("playWithTamagotchi", async () => {
    const response = await tamagotchiService.playWithTamagotchi()

    if (response) {
        handleResponse(response)
    } else {
        window.alert("Failed to play with the Tamagotchi")
    }
})

tamagotchiScreen.addEventListener("disconnectTamagotchiEvent", async () => {
    await tamagotchiService.disconnectTamagotchi()
    goToLoginScreen()
})


function handleResponse(response) {
    tamagotchiScreen.tamagotchi = response.tamagotchi
    tamagotchiScreen.updateChat(response.historyAction)
}