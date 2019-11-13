package logan;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import static logan.main.danmus;
import static logan.main.room;

public class DanMuView extends Frame implements Runnable {
//    private List<DanMu> danmus = new ArrayList<DanMu>() {{
//        add(new DanMu(1440, 100, "hello"));
//    }};
    private int windowwidth;
    private int windwoheight;

    private int frame = 60;


    public DanMuView() {
        this.setUndecorated(true); // 禁用或启用此窗体的修饰。只有在窗体不可显示时
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获取桌面的大小
        Rectangle bounds = new Rectangle(screenSize);
        windwoheight = (int) screenSize.getHeight();
        windowwidth = (int) screenSize.getWidth();

        this.setBounds(bounds);                                          //把窗口设为桌面大小
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        // 判断是否支持透明度

//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setExtendedState(Frame.MAXIMIZED_BOTH);    //窗口最大化
        AWTUtilities.setWindowOpaque(this, false);//把窗口设为透明
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        int st = 0;
//        if(list.size()>maxScreenDanmuCount) st=list.size()-maxScreenDanmuCount;
        for (int i = st; i < danmus.size(); i++) {  //绘制list里面的从st开始有字符串
            DanMu danmu = danmus.get(i);
            if (danmu.x < -windowwidth) {
                danmus.remove(danmu);
            }
            Font font = new Font("Dialog", Font.BOLD, 32);
            graphics.setColor(Color.RED);
            graphics.setFont(font);

            FontMetrics fm = graphics.getFontMetrics(font);
            int width = fm.stringWidth(danmu.getContent());

            room[danmu.getChannel()] = danmu.x + width;
//            for (int j = 0; j<10;j++) System.out.print(room[j] + "  ");
//            System.out.println("\n");
            graphics.drawString(danmu.getContent(), danmu.x, danmu.y);
        }
    }

    private void refresh() {
        Timer timer = new Timer();
        timer.schedule(new ReschedulableTimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (danmus.size() == 0) {
                    if (this.getPeriod() != 1000) this.setPeriod(1000);
                    return;
                } else {
                    if (this.getPeriod() == 1000) this.setPeriod(1000 / frame);
                }
                int st = 0;              //list的起始遍历位置
                for (int i = st; i < danmus.size(); i++) {
                    DanMu d = danmus.get(i);
                    d.x -= 1;                  //  改变弹幕的x坐标
                }
                repaint();
            }
        }, 100, 1000 / frame);//定时执行刷新
    }

    private static DanMuView instance = null;
    private static Semaphore semaphore = new Semaphore(0);


    static synchronized DanMuView getInstance() {
        if (instance == null) {
            try {
                showDanmu();
                semaphore.acquire();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return instance;
        }
        return instance;
    }

    private static void showDanmu() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                instance = new DanMuView();
                instance.refresh();
                semaphore.release();
            }
        });
    }

    @Override
    public void run() {

    }
}
