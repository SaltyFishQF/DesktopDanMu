from PyQt5.Qt import *
from MyWidget import DmView
import sys

print("初始化弹幕参数...")
dmset = set()
print("完成！")

print("开启弹幕装填器...")
app = QApplication(sys.argv)
w = DmView(sys.argv[1])
w.show()
print("完成！")
sys.exit(app.exec_())
