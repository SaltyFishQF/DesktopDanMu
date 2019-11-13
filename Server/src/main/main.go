package main

import (
	"fmt"
	"handler"
	"net/http"
	"os"
)

func main() {
	http.HandleFunc("/syncMsg", handler.SyncMsg)
	http.HandleFunc("/newMsg", handler.NewMsg)
	if err := http.ListenAndServe(":59335", nil); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}
