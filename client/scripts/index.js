let loginScreen = document.getElementById("login-screen")
let tamagotchiScreen = document.getElementById("tamagotchi-screen")
let buttonConnect = document.getElementById("button-connect")
let screenSelected = loginScreen

//EVENTS
buttonConnect.addEventListener("click", function (ev) {
    ev.preventDefault()
    loginScreen.style.display = "none"
    tamagotchiScreen.style.display = "block"
})

if (loginScreen == screenSelected) {
    tamagotchiScreen.style.display = "none"
}