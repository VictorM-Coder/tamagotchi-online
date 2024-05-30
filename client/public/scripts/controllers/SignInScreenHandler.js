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