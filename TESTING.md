# APK 测试指南

## 🚀 快速开始

### 方式一：本地构建（推荐）

```bash
# 1. 克隆项目
git clone https://github.com/kiszu/stock-analysis-app
cd stock-analysis-app

# 2. 设置Android SDK
export ANDROID_HOME=/path/to/your/android-sdk

# 3. 构建调试APK
./build.sh debug

# 4. 安装到手机
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 方式二：使用GitHub Actions构建的APK

如果GitHub Actions已运行：
1. 访问 https://github.com/kiszu/stock-analysis-app/releases
2. 下载最新的APK
3. 传输到手机并安装

---

## 📱 测试检查清单

### 基本功能测试

- [ ] **启动应用**
  - [ ] 应用正常启动，无崩溃
  - [ ] 启动时间 < 3秒
  - [ ] 加载动画正常显示

- [ ] **Dashboard界面**
  - [ ] 显示Featured Signals
  - [ ] 显示Market Overview
  - [ ] 顶部导航栏显示
  - [ ] 股票列表滚动流畅

- [ ] **信号卡片**
  - [ ] 显示正确的股票代码（AAPL, MSFT等）
  - [ ] 信号类型显示正确（BUY/SELL/HOLD）
  - [ ] 置信度百分比正确
  - [ ] 颜色编码正确（绿色=涨, 红色=跌）
  - [ ] 点击卡片跳转到详情页

- [ ] **信号详情页**
  - [ ] 显示完整股票信息
  - [ ] 技术指标显示正确
  - [ ] 置信度进度条正常
  - [ ] 支持/阻力位显示
  - [ ] 返回按钮正常工作

- [ ] **搜索功能**
  - [ ] 搜索框可点击
  - [ ] 输入股票代码可搜索
  - [ ] 搜索结果显示
  - [ ] 点击结果跳转到详情

- [ ] **暗色模式**
  - [ ] 系统暗色模式下正常显示
  - [ ] 颜色对比度正确

---

## 🐛 常见问题

### Q: 构建失败 "ANDROID_HOME not set"

```bash
# macOS/Linux
export ANDROID_HOME=$HOME/Library/Android/sdk

# Windows
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk

# 或安装Android Studio，自动设置
```

### Q: adb: command not found

```bash
# macOS
brew install android-platform-tools

# Linux
sudo apt install android-tools-adb

# Windows
# 安装 Android Studio 或 platform-tools
```

### Q: APK安装失败 "INSTALL_FAILED_VERIFICATION_FAILURE"

```bash
# 启用未知来源
# 设置 > 安全 > 未知来源 > 允许

# 或在开发者选项中
# 设置 > 开发者选项 > USB调试
```

---

## 📊 测试数据说明

原型使用以下模拟数据：

| 股票 | 名称 | 信号 | 置信度 | 价格 | 涨跌 |
|------|------|------|--------|------|------|
| AAPL | Apple Inc. | BUY | 87% | $178.50 | +3.8% |
| MSFT | Microsoft | STRONG_BUY | 92% | $378.91 | +5.2% |
| GOOGL | Alphabet | BUY | 78% | $141.80 | +2.1% |
| TSLA | Tesla | SELL | 72% | $248.50 | -4.3% |
| NVDA | NVIDIA | STRONG_BUY | 95% | $495.22 | +8.5% |
| AMZN | Amazon | BUY | 81% | $153.42 | +2.8% |

---

## 📝 测试反馈模板

请反馈以下信息：

```
设备型号: [例如 iPhone 15 / Pixel 7]
Android版本: [例如 14]
APK版本: 1.0.0

测试项目:
1. Dashboard加载: [正常/异常]
2. 信号显示: [正常/异常]
3. 搜索功能: [正常/异常]
4. 详情页: [正常/异常]

发现的Bug:
1. [描述]
2. [描述]

建议改进:
1. [建议]
2. [建议]
```

---

## 🔗 相关链接

- GitHub仓库: https://github.com/kiszu/stock-analysis-app
- 构建状态: https://github.com/kiszu/stock-analysis-app/actions
- 最新Release: https://github.com/kiszu/stock-analysis-app/releases
- Issues: https://github.com/kiszu/stock-analysis-app/issues

---

**测试愉快！🎉**
