# DesktopDanMu
Golang+PyQT5实时桌面弹幕  

### 1. 服务器端  
下载[release](https://github.com/SaltyFishQF/DesktopDanMu/releases/)中对应的版本
```bash
chmod +x main
./main
```

### 2. Python客户端  
基于PyQt5编写，仅测试了在MacOS Catalina下正常运行。需要外部依赖PyQt5，若没有请安装：
```bash
pip install PyQt5
```  

然后运行
```bash
python PyClient/main.py
```

### ~~3. Java客户端~~  
在MacOS Catalina下正常运行，windows下似乎无法显示弹幕，且运行时CPU占用率高，放弃更新。
