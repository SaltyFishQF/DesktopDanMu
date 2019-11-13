from PyQt5.Qt import *
import json


class DmWidget(QPushButton):
    def __init__(self, parent, content, x=0, y=0, color=None, font=None, channel=None):
        super().__init__(content, parent)
        # self.color(color)
        self.setFont(QFont('宋体', 30, 50))
        self.channel = channel
        window_width = QDesktopWidget().screenGeometry().width()
        style_sheet = "QPushButton{background-color: rgba(97%,80%,9%,1%);border:none;color:" + "rgb(100%, 0%, 0%)" + ";}"
        self.setStyleSheet(style_sheet)
        self.adjustSize()
        self.anim = QPropertyAnimation(self, b"pos")
        self.anim.setDuration(20000)
        self.anim.setStartValue(QPoint(window_width, self.channel * 50 + 50))
        self.anim.setEndValue(QPoint(-self.width(), self.channel * 50 + 50))
        self.anim.setEasingCurve(QEasingCurve.Linear)
        self.anim.finished.connect(self.end)
        self.anim.start()

    def end(self):
        self.deleteLater()
        self.parent = None
        self = None


class DmView(QWidget):
    def __init__(self, url):
        super().__init__()
        print("尝试连接弹幕服务器...")
        self.url = url
        self.dataRecvws = QWebSocket()
        self.config = self.dataRecvws.sslConfiguration()  # ssl认证之类的
        self.config.setPeerVerifyMode(QSslSocket.VerifyNone)
        self.config.setProtocol(QSsl.TlsV1SslV3)
        self.dataRecvws.setSslConfiguration(self.config)

        self.dataRecvws.disconnected.connect(self.onDisconnect)  # 断开连接
        self.dataRecvws.textMessageReceived.connect(self.onTextReceived)
        # self.dataRecvws.binaryMessageReceived.connect(self.onBinaryReceived)#数据接收
        self.dataRecvws.connected.connect(self.onConnected)

        self.showMaximized()
        self.setWindowOpacity(1)
        self.setFocusPolicy(Qt.NoFocus)
        self.setWindowFlags(Qt.WindowStaysOnTopHint | Qt.FramelessWindowHint | Qt.X11BypassWindowManagerHint)
        self.setAttribute(Qt.WA_TranslucentBackground)

        self.dataRecvws.open(QUrl(url))

    def onConnected(self):
        print("连接成功！")

    def onTextReceived(self, message):
        if message == '':
            return
        print("--->Msg:" + message)
        channel = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

        for i in self.children():
            if i.x() + i.width() > channel[i.channel]:
                channel[i.channel] = i.x() + i.width()

        for i in range(len(channel)):
            if channel[i] < QDesktopWidget().screenGeometry().width() - 200:
                dict = json.loads(message, strict=False)
                DmWidget(self, dict["Content"], channel=i).show()
                break
    def onDisconnect(self):
        print("连接丢失, 尝试重连")
        self.dataRecvws.open(QUrl(self.url))