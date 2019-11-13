package logan;

public class DanMu {
    public int x = 0;
    public int y = 0;
    private int channel;
    private int endPos;
    private String content;

    public DanMu(int x, int y, int channel, String content) {
        this.x = x;
        this.y = y;
        this.channel = channel;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }
}
