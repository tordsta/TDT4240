var app = require("express")();
var server = require("http").createServer(app);
var io = require("socket.io")(server);

const PORT = 3000
// TODO: Support multiple rooms
const ROOM_0 = "room0"
const TURN_DURATION = 20000 // ms

let rotateID

server.listen(PORT, () => {
  console.log(`Server is now running on port ${PORT}`);
});

const initState = {
  rooms: {
    [ROOM_0]: {
      players: [],
      spawns: [],
      roomID: ROOM_0
    }
  },
  entities: {
    [ROOM_0]: {}
  }
}

let DB = {...initState}
let nextIndex = 0
let rotate = false

io.on("connection", socket => {
  console.log("Player Connected!");

  const playerID = socket.id

  socket.on("login", () => {
    const room = DB.rooms[ROOM_0]
    socket.emit("logged-in", {room, newPlayerID: playerID})
    socket.broadcast.emit("logged-in", {room, newPlayerID: playerID})
  })

  socket.on("update-entities", entities => {
    DB.entities[ROOM_0] = entities
    socket.broadcast.emit("entities", DB.entities[ROOM_0])
  })

  socket.on("room-update", newRoom => {
    console.log("room update")
    console.log(newRoom, newRoom.players)
    DB.rooms[newRoom.roomID] = newRoom
    if (DB.rooms[newRoom.roomID].players.length > 1 && !rotate) {
      rotateTurns()
      rotate = true
    }
  })


  socket.on("leave-room", () => {
    const players = DB.rooms[ROOM_0].players
    players.splice(players.findIndex(p => p === playerID), 1)
    const entities = DB.entities[ROOM_0]
    delete entities[Object.keys.find(key => Object.values(entries[key]).includes(playerID))]
    // TODO: clenaup rotation of players in the room.
    // TODO: If last in the room, remove the room.
  })

  socket.on("delete-entity", networkID => {
    const entity = DB.entities[ROOM_0][networkID]
    if(entity.type === "PLAYER") {
      DB.rooms[ROOM_0].players.splice(DB.rooms[ROOM_0].players.findIndex(p => p === entity.playerID))
    }
    delete DB.entities[ROOM_0][networkID]

    socket.emit("delete-entity-triggered", {networkID: networkID})
    socket.broadcast.emit("delete-entity-triggered", {networkID: networkID})
  })

  // Disconnect
  socket.on("disconnect", () => {
    console.log("Player Disconnected!");
    if(DB.rooms[ROOM_0].players.length === 1){
      DB = {...initState}
    }
    clearInterval(rotateID)
    rotate = false


  
    // leave room
    if(DB.rooms[ROOM_0].players){
      DB.rooms[ROOM_0].players.splice(DB.rooms[ROOM_0].players.findIndex(p => p === playerID))
    } 
    
    socket.leaveAll()
  }); 
  
  function rotateTurns() {
    rotateID = setInterval(() => {
      const {players} = DB.rooms[ROOM_0]
      const nextPlayer = players[nextIndex]
      console.log("rotating turns, new player is: ", nextPlayer )
      socket.emit("player-change", {playerID: nextPlayer})
      socket.broadcast.emit("player-change", {playerID: nextPlayer})
      if (nextIndex === players.length - 1) nextIndex = 0
      else nextIndex++
    }, TURN_DURATION)
  }
});
