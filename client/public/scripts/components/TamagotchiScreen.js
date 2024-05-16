class TamagotchiScreenComponent extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
            <main id="tamagotchi-screen" class="pixel-border tamagotchi-screen">
                <section class="cat-container">
                    <div>
                        <div class="cat-box pixel-border mb-2">
                            <img src="assets/cat-default.gif" alt="Cat image" class="image-fluid">
                        </div>
                        <div class="status">
                            <span class="status-bar pixel-border">
                                <img src="assets/happy-icon.png" alt="Happy face icon" class="image-fluid">
                            </span>
                            <span class="status-bar pixel-border">
                                <img src="assets/meal-icon.png" alt="Fish meal icon" class="image-fluid">
                            </span>
                            <span class="status-bar pixel-border">
                                <img src="assets/energy-icon.png" alt="Energy thunder icon" class="image-fluid">
                            </span>
                        </div>
                    </div>
                    <div class="cat-actions">
                        <div class="chat pixel-border mb-2">
                            Chat
                        </div>
                        <div class="action-bar">
                            <button class="mt-2">Feed</button>
                            <button class="mt-2">Sleep</button>
                            <button class="mt-2">Play</button>
                            <button class="mt-2">Disconect</button>
                        </div>
                    </div>
                </section>
            </main>
        `


    }
}

customElements.define("tamagotchi-screen", TamagotchiScreenComponent)