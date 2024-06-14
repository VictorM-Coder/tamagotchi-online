loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", async (ev) => {
    const username = ev.detail.username
    const id = ev.detail.id

    const response = await tamagotchiService.getTamagotchi(username, id)
    if (response) {
        tamagotchiScreen.tamagotchi = response.tamagotchi
        tamagotchiScreen.history = response.history
        LocalstorageUtils.setUserAndId(username, id)
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to connect to the Tamagotchi")
    }
})