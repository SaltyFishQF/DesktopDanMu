package handler

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

func NewMsg(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	w.Header().Add("Access-Control-Allow-Headers", "Content-Type")
	if body, err := ioutil.ReadAll(r.Body); err == nil {
		SendMsg(&WS, fmt.Sprintf("%s", body))
		w.Write([]byte("success"))
	} else {
		panic(err)
	}
}
