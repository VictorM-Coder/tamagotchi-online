class TamagotchiScreenComponent extends HTMLElement {
    constructor() {
        super();
        this._tamagotchi = null
    }
    isWaiting = false

    updateData() {
        const pbEnergy = document.getElementById("pb-energy")
        const pbHungry = document.getElementById("pb-hungry")
        const pbHappiness = document.getElementById("pb-happiness")
        const imgCat = document.getElementById("img-cat")


        if (pbEnergy && pbHungry && pbHappiness) {
            pbEnergy.value = this.tamagotchi.energy
            pbHungry.value = this.tamagotchi.food
            pbHappiness.value = this.tamagotchi.happy

            if (!this.isWaiting) {
                this.enableActions()
                imgCat.src = (!this.tamagotchi.isSleeping)? "assets/cat-sprites/cat-default.gif": "assets/cat-sprites/sleeping-cat.gif"
            }
        }
    }

    connectedCallback() {
        this.innerHTML = `
            <main id="tamagotchi-screen" class="pixel-border tamagotchi-screen">
                <section class="cat-container">
                    <div>
                        <header id="tamagotchi-header" class="mb-2">
                            Name - age
                        </header>
                        <div class="cat-box pixel-border mb-2">
                            <img id="img-cat" src="assets/cat-default.gif" alt="Cat image" class="image-fluid">
                        </div>
                        <div class="status">
                            <span class="status-bar pixel-border">
                                <progress class="progress-bar" value="10" max="100" id="pb-happiness"> </progress>
                                <img src="assets/icons/happy-icon.png" alt="Happy face icon" class="image-fluid icon-progress-bar">
                            </span>
                            <span class="status-bar pixel-border">
                                <progress class="progress-bar" value="10" max="100" id="pb-hungry"> </progress>
                                <img src="assets/icons/meal-icon.png" alt="Fish meal icon" class="image-fluid icon-progress-bar">
                            </span>
                            <span class="status-bar pixel-border">
                                <progress class="progress-bar" value="10" max="100" id="pb-energy"> </progress>
                                <img src="assets/icons/energy-icon.png" alt="Energy thunder icon" class="image-fluid icon-progress-bar">
                            </span>
                        </div>
                    </div>
                    <div class="cat-actions">
                        <div class="chat pixel-border mb-2">
                            Chat
                        </div>
                        <div class="action-bar">
                            <button id="btn-feed" class="mt-2">
                                Feed
                            </button>
                            <button id="btn-sleep" class="mt-2">
                                Sleep
                            </button>
                            <button id="btn-play" class="mt-2">
                                Play
                            </button>
                            <button class="mt-2">
                                Disconect
                            </button>
                        </div>
                    </div>
                </section>
            </main>
        `

        const header = document.getElementById("tamagotchi-header")

        const btnFeed = document.getElementById("btn-feed")
        const btnSleep = document.getElementById("btn-sleep")
        const btnPlay = document.getElementById("btn-play")
        const imgCat = document.getElementById("img-cat")

        header.innerText = `${this.tamagotchi.name} - ${this.tamagotchi.ageInDays} Days`
        this.updateData()

        //EVENTS
        btnSleep.addEventListener("click", () => {
            const putTamagochiToSleepEvent = new CustomEvent("putTamagochiToSleepEvent", {
                bubbles: true,
            })
            this.dispatchEvent(putTamagochiToSleepEvent)
        })

        btnFeed.addEventListener("click", () => {
            this.disableActions()
            imgCat.src = "assets/cat-sprites/cat-eating.gif"
            this.isWaiting = true

            setTimeout(() => {
                imgCat.src = "assets/cat-sprites/cat-default.gif"
                this.isWaiting = false
                this.enableActions()
                const feedTamagochiEvent = new CustomEvent("feedTamagotchi", {
                    bubbles: true,
                })
                this.dispatchEvent(feedTamagochiEvent)
            }, 3000)

        })

        btnPlay.addEventListener("click", () => {
            this.disableActions()
            imgCat.src = "assets/cat-sprites/cat-jumping.gif"
            this.isWaiting = true

            setTimeout(() => {
                imgCat.src = "assets/cat-sprites/cat-default.gif"
                this.isWaiting = false
                this.enableActions()

                const playWithTamagotchi = new CustomEvent("playWithTamagotchi", {
                    bubbles: true,
                })
                this.dispatchEvent(playWithTamagotchi)
            }, 3000)
        })
    }

    disableActions() {
        const btnFeed = document.getElementById("btn-feed")
        const btnSleep = document.getElementById("btn-sleep")
        const btnPlay = document.getElementById("btn-play")

        btnPlay.disabled = true
        btnFeed.disabled = true
        btnSleep.disabled = true
    }

    enableActions() {
        const btnFeed = document.getElementById("btn-feed")
        const btnSleep = document.getElementById("btn-sleep")
        const btnPlay = document.getElementById("btn-play")

        btnPlay.disabled = false
        btnFeed.disabled = false
        btnSleep.disabled = false
    }

    set tamagotchi(value) {
        if (typeof value === "object" && value !== null) {
            this._tamagotchi = value;
            this.updateData()
        }
    }

    get tamagotchi() {
        return this._tamagotchi;
    }
}

customElements.define("tamagotchi-screen", TamagotchiScreenComponent)