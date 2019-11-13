package logan;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

import static logan.main.*;

public class WsClient extends WebSocketClient {


    public WsClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("正在打开WebSocket通道");
    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
        JSONObject json = new JSONObject(s);
        String content = json.getString("Content");
        int color = json.getInt("Color");
        try {
            danmus.add(allocateRoom(WIDTH, content));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("close");
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e.getMessage());
    }
}
