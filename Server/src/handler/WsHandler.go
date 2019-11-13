package handler

import (
	"fmt"
	"github.com/gorilla/websocket"
	"log"
	"net/http"
)

var WS = websocket.Conn{}
var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

func SyncMsg(w http.ResponseWriter, r *http.Request) {
	ws, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		log.Print("upgrade:", err)
		return
	}
	WS = *ws
	defer ws.Close()
	for {
		mt, message, err := ws.ReadMessage()
		if err != nil {
			log.Println("read:", err)
			break
		}
		log.Printf("recv: %s", message)
		err = ws.WriteMessage(mt, message)
		if err != nil {
			log.Println("write:", err)
			break
		}
	}
}

func SendMsg(ws *websocket.Conn, data string) {
	var err error
	if err = ws.WriteMessage(websocket.TextMessage, []byte(data)); err != nil {
		fmt.Println(err)
	}
	fmt.Println(data)
}
