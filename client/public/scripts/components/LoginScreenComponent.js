class LoginScreenComponent extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
            <main id="login-screen" class="pixel-border tamagotchi-screen">
                <h1 class="title-h1">Welcome to online Tamagotchi</h1>
                <form class="form-login mt-4">
                    <div class="form-internal">
                        <div class="mb-3">
                            <label for="input-username" class="d-block">Who are you?</label>
                            <input id="input-username" type="text" class="input-tamagotchi d-block mt-2 pixel-border">
                        </div>
            
                        <div class="mb-3">
                            <label for="input-id" class="d-block">Where is your pet?</label>
                            <input id="input-id" type="number" min="1" class="input-tamagotchi pixel-border d-block mt-2">
                        </div>
                    </div>
                    <div class="action-bar-login">
                        <button id="button-signin" class="margin-start-auto me-3">Don't have a Tamagotchi</button>
                        <button id="button-connect">Connect</button>
                    </div>
                </form>
            </main>
        `

        const buttonConnect = document.getElementById("button-connect")
        const buttonSignIn = document.getElementById("button-signin")
        const inputUsername = document.getElementById("input-username")
        const inputId = document.getElementById("input-id")

        buttonConnect.addEventListener("click", function (ev) {
            ev.preventDefault()
            const username = inputUsername.value
            const id = inputId.value

            if (username.length === 0) {
                window.alert("Fill your name, please")
            } else if (!id) {
                window.alert("Fill id input with a valid value")
            } else {
                const connectEvent = new CustomEvent("connectEvent",
                    {
                        bubbles: true,
                        detail: {
                            username: username,
                            id: id
                        }
                    }
                );
                this.dispatchEvent(connectEvent);
            }
        })

        buttonSignIn.addEventListener("click", function (ev) {
            ev.preventDefault()
            const signInEvent = new CustomEvent("signInEvent", { bubbles: true });
            this.dispatchEvent(signInEvent);
        })
    }
}

customElements.define("login-screen", LoginScreenComponent)
