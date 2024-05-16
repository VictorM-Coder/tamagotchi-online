const express = require("express")
const net = require('net');
const {json} = require("express");
const tamagotchiController = require("./api/TamagotchiController");

const app = express()

app.use(express.static("public"))
app.use(json());
app.use("/api", tamagotchiController)

app.listen(3000, () => {
    console.log("Site available in http://localhost:3000")
})