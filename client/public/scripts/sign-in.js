const buttonCreateTamagotchi = document.getElementById("button-complete-signin")
const buttonCancelSignIn = document.getElementById("button-cancel-signin")

const inputUsername = document.getElementById("inputSignTamagotchiId")
const inputTamagotchiName = document.getElementById("inputTamagotchiName")
const inputTamagotchiId = document.getElementById("inputSignTamagotchiId")


//EVENTS
buttonCreateTamagotchi.addEventListener("click", async function (ev) {
    ev.preventDefault()

    const username = inputUsername.value
    const tamagotchiName = inputTamagotchiName.value
    const id = inputTamagotchiId.value

    if (username.length === 0) {
        window.alert("Please, fill the name field")
    } else if (tamagotchiName.length === 0) {
        window.alert("Please, fill the tamagotchi name field")
    } else if (tamagotchiName.indexOf(' ') >= 0) {
        window.alert("Name must not have empty space characters")
    } else if (id.length === 0) {
        window.alert("Please, fill the ip field")
    } else {
        const response = await createTamagotchi(username, tamagotchiName)
        if (response) {
            goToTamagotchiScreen(response)
        } else {
            window.alert("Falha ao criar o Tamagotchi")
        }
    }
})

buttonCancelSignIn.addEventListener("click", function (ev) {
    ev.preventDefault()
    goToLoginFromSignInScreen()
})