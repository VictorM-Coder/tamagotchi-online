class ChatActionComponent extends HTMLElement {
    constructor() {
        super();
        this._chatAction = null
    }

    connectedCallback() {
        this.innerHTML = `
            <div class="chat-message">[${this.chatAction.dateTime}]<strong>${this.chatAction.username}</strong>: ${this.chatAction.action}</div>
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
}

customElements.define("chat-action", ChatActionComponent)
