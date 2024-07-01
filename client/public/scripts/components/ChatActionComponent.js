class ChatActionComponent extends HTMLElement {
    constructor() {
        super();
        this._chatAction = null
    }

    connectedCallback() {
        this.innerHTML = `
            <div class="chat-message">[${this.getDateTimeFormated()}]<strong>${this.chatAction.username}</strong> ${this.getActionMessage()}</div>
        `
    }

    get chatAction() {
        return this._chatAction
    }

    set chatAction(value) {
        if (value) {
            this._chatAction = value
        }
    }

    getDateTimeFormated() {
        const date = new Date(this.chatAction.dateTime)
        return date.toLocaleString('en-US', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' })
    }

    getActionMessage() {
        switch (this.chatAction.action) {
            case 'EAT':
                return 'fed the Tamagotchi.';
            case 'SLEEP':
                return 'put the Tamagotchi to sleep.';
            case 'AWAKE':
                return 'woke up the Tamagotchi.';
            case 'PLAY':
                return 'played with the Tamagotchi.';
            case 'CREATE':
                return 'created the Tamagotchi.';
        }
    }
}

customElements.define("chat-action", ChatActionComponent)
