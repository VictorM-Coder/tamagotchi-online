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
