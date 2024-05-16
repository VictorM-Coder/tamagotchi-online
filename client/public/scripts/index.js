const loginScreen = document.getElementById("login-screen")
const tamagotchiScreen = document.getElementById("tamagotchi-screen")
const signInScreen = document.getElementById("signin-screen")

const buttonConnect = document.getElementById("button-connect")
const buttonSignIn = document.getElementById("button-signin")

const screenSelected = loginScreen

let tamagotchi = null

if (loginScreen === screenSelected) {
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
function goToTamagotchiScreen(tamagotchiData) {
    tamagotchi = tamagotchiData
    loginScreen.style.display = "none"
    signInScreen.style.display = "none"
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