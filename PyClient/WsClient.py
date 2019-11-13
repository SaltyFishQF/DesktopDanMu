import websocket

def on_error(ws, error):
    print(error)


def on_close(ws):
    print("### closed ###")


def on_open(ws):
    print("Socket连接建立成功!")


def getConn(url, on_message):
    websocket.enableTrace(True)
    ws = websocket.WebSocketApp(url,
                                on_message = on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()
    return ws
