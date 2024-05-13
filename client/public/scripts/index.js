let loginScreen = document.getElementById("login-screen")
let tamagotchiScreen = document.getElementById("tamagotchi-screen")
let signInScreen = document.getElementById("signin-screen")

let buttonConnect = document.getElementById("button-connect")
let buttonSignIn = document.getElementById("button-signin")

let screenSelected = loginScreen

if (loginScreen == screenSelected) {
    tamagotchiScreen.style.display = "none"
    signInScreen.style.display = "none"
}

//EVENTS
buttonConnect.addEventListener("click", function (ev) {
    ev.preventDefault()
    goToTamagotchiScreen()
})

buttonSignIn.addEventListener("click", function (ev) {
    ev.preventDefault()
    goToSignInScreen()
})


//AUX FUNCTIONS
function goToTamagotchiScreen() {
    loginScreen.style.display = "none"
    tamagotchiScreen.style.display = "block"
}

function goToSignInScreen() {
    loginScreen.style.display = "none"
    signInScreen.style.display = "block"
}

function goToLoginFromSignInScreen() {
    signInScreen.style.display = "none"
    loginScreen.style.display = "block"
}