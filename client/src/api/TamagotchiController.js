const express = require("express")
const net = require("net");

const router = express.Router();
const TamagotchiService = require("./TamagotchiService");
const service = new TamagotchiService()

router.post('/create', async (req, res) => {
    const username = req.body.username
    const tamagotchiName = req.body.tamagotchiName

    const response = await service.createTamagotchi(username, tamagotchiName)
    res.send(response)
});

router.put('/eat', async (req, res) => {
    const username = req.body.username
    const id = req.body.id

    const response = await service.feedTamagotchi(username, id)
    res.send(response)
});

router.put('/play', async (req, res) => {
    const username = req.body.username
    const id = req.body.id

    const response = await service.playWithTamagotchi(username, id)
    res.send(response)
});

router.put('/sleep', async (req, res) => {
    const username = req.body.username
    const id = req.body.id

    const response = await service.putTamagotchiToSleep(username, id)
    res.send(response)
});

router.put('/awake', async (req, res) => {
    const username = req.body.username
    const id = req.body.id

    const response = await service.awakeTamagotchi(username, id)
    res.send(response)
});

router.get('/get', async (req, res) => {
    const username = req.query.username
    const id = req.query.id

    const response = await service.getTamagotchi(username, id)
    res.send(response)
});

module.exports = router