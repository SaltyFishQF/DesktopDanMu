package logan;

import org.java_websocket.enums.ReadyState;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static List<DanMu> danmus = new ArrayList<>();
    public static int[] room = new int[10];
    public static int WIDTH;

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        System.out.println("初始化弹幕引擎...");
        DanMuView view = DanMuView.getInstance();
        WIDTH = view.getWidth();
        System.out.println("完成！");

        System.out.println("初始化参数...");
        for (int v : room) v = 0;
        System.out.println("完成！");

        WsClient wc = new WsClient(new URI("ws://www.logan-qiu.cn:59335/syncMsg"));
        boolean f = wc.connectBlocking();
        System.out.println("正在建立websocket连接: "+ f);
        System.out.println("完成！");
    }

    public static DanMu allocateRoom(int width, String content) throws InterruptedException {
        while (true) {
            for (int i = 0; i < 10; i++) {
                if (room[i] < width - 200) {
                    return new DanMu(width, i * 50 + 50, i, content);
                }
            }
            Thread.sleep(1000);
        }
    }

}
