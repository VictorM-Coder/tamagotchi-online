const express = require("express")
const net = require("net");

const router = express.Router();
const TamagotchiService = require("./TamagotchiService");
let service

router.get('/connect', async (req, res) => {
    service = createServiceIfNotExists()
    const username = req.query.username
    const id = req.query.id

    const response = await service.connectToTamagotchi(username, id)
    res.send(response)
});


router.post('/create', async (req, res) => {
    service = createServiceIfNotExists()
    const username = req.body.username
    const tamagotchiName = req.body.tamagotchiName

    const response = await service.createTamagotchi(username, tamagotchiName)
    res.send(response)
});

router.put('/eat', async (req, res) => {
    const response = await service.feedTamagotchi()
    res.send(response)
});

router.put('/play', async (req, res) => {
    const response = await service.playWithTamagotchi()
    res.send(response)
});

router.put('/sleep', async (req, res) => {
    const response = await service.putTamagotchiToSleep()
    res.send(response)
});

router.put('/awake', async (req, res) => {
    const response = await service.awakeTamagotchi()
    res.send(response)
});

router.get('/get', async (req, res) => {
    const response = await service.getTamagotchi()
    res.send(response)
});

router.get('/end', async (req, res) => {
    const response = await service.disconnectTamagotchi()
    res.send(response)
});

function createServiceIfNotExists() {
    if (service) {
        return service
    } else {
        return new TamagotchiService()
    }
}

module.exports = router