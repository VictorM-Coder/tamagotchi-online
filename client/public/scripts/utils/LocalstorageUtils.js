class LocalstorageUtils {
    static setUserAndId(username, id) {
        localStorage.setItem("username", username)
        localStorage.setItem("id", id)
    }

    static getUser() {
        const username = localStorage.getItem("username")
        const id = localStorage.getItem("id")

        return {
            username: username,
            id: id
        }
    }
}