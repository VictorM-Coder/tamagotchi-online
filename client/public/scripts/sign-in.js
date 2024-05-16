let buttonCreateTamagotchi = document.getElementById("button-complete-signin")
let buttonCancelSignIn = document.getElementById("button-cancel-signin")

let inputTamagotchiName = document.getElementById("inputTamagotchiName")
let inputTamagotchiId = document.getElementById("inputSignTamagotchiId")


//EVENTS
buttonCreateTamagotchi.addEventListener("click", async function (ev) {
    ev.preventDefault()

    let name = inputTamagotchiName.value
    let id = inputTamagotchiId.value

    if (name.length === 0) {
        window.alert("Please, fill the name field")
    } else if (name.indexOf(' ') >= 0) {
        window.alert("Name must not have empty space characters")
    } else if (id.length === 0) {
        window.alert("Please, fill the ip field")
    } else {
        //TODO melhorar a lógica para criação: adicionar um novo campo para o nome do usuário
        await createTamagotchi("victor", "vlade")
    }
})

buttonCancelSignIn.addEventListener("click", function (ev) {
    ev.preventDefault()
    goToLoginFromSignInScreen()
})