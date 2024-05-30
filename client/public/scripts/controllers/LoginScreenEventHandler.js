loginScreen.addEventListener("signInEvent", () => {
    goToSignInScreen()
})

loginScreen.addEventListener("connectEvent", async (ev) => {
    const username = ev.detail.username
    const id = ev.detail.id

    const response = await tamagotchiService.getTamagotchi(username, id)
    if (response) {
        console.log(response)
        tamagotchiScreen.tamagotchi = response.tamagotchi
        LocalstorageUtils.setUserAndId(username, id)
        goToTamagotchiScreen()
    } else {
        window.alert("Failed to connect to the Tamagotchi")
    }
})