class SingInScreenComponent extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
            <main id="signin-screen" class="pixel-border tamagotchi-screen">
                <h1 class="title-h1">Create your own Tamagotchi!</h1>
                <form class="form-login mt-4">
                    <div class="form-internal">
                        <div class="mb-3">
                            <label for="inputSignUsername" class="d-block">What's your name?</label>
                            <input id="inputSignUsername" type="text" class="input-tamagotchi pixel-border d-block mt-2">
                        </div>
            
                        <div class="mb-3">
                            <label for="inputTamagotchiName" class="d-block">Give your pet a name!</label>
                            <input id="inputTamagotchiName" type="text" class="input-tamagotchi d-block mt-2 pixel-border">
                        </div>
                    </div>
                    <div class="action-bar-login">
                        <button id="button-cancel-signin" class="margin-start-auto me-3">Cancel</button>
                        <button id="button-complete-signin">Create Tamagotchi</button>
                    </div>
                </form>
            </main>
        `

        const buttonCreateTamagotchi = document.getElementById("button-complete-signin")
        const buttonCancelSignIn = document.getElementById("button-cancel-signin")

        const inputUsername = document.getElementById("inputSignUsername")
        const inputTamagotchiName = document.getElementById("inputTamagotchiName")


        buttonCreateTamagotchi.addEventListener("click", async function (ev) {
            ev.preventDefault()

            const username = inputUsername.value
            const tamagotchiName = inputTamagotchiName.value

            if (username.length === 0) {
                window.alert("Please, fill the name field")
            } else if (tamagotchiName.length === 0) {
                window.alert("Please, fill the tamagotchi name field")
            } else if (tamagotchiName.indexOf(' ') >= 0) {
                window.alert("Name must not have empty space characters")
            } else {
                const tamagotchiSubmited = new CustomEvent("tamagotchiSubmited", {
                    bubbles: true,
                    detail: {
                        username: username,
                        tamagotchiName: tamagotchiName,
                    }
                })
                this.dispatchEvent(tamagotchiSubmited)
            }
        })

        buttonCancelSignIn.addEventListener("click", function (ev) {
            ev.preventDefault()
            goToLoginScreen()
        })
    }
}

customElements.define("sign-in-screen", SingInScreenComponent)
