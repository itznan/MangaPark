# MangaPark Reader - Android App

A lightweight and efficient Android application for reading manga from MangaPark with enhanced features and offline capabilities.

## 📱 Key Features

- 💨 Fast and responsive WebView interface
- 💾 Smart image caching system for offline reading
- 🌙 Automatic cache cleanup at midnight
- 📱 Fullscreen reading mode for better experience
- 🔄 Pull-to-refresh functionality
- 🎯 Optimized for performance and battery life

## 🛠️ Technical Requirements

- Android Studio or Command Line Tools
- JDK 17 or higher
- Android SDK 35 (Android 14)
- Gradle 8.0+

## 🚀 Quick Start

### Building from Source

1. **Clone the Repository**
   ```bash
   git clone https://github.com/itznan/mangapark.git
   cd MangaPark
   ```

2. **Build the APK**
   ```bash
   ./gradlew assembleDebug
   ```
   The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

3. **Install on Device**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## 🏗️ Project Architecture

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nan/mangaparkreader/
│   │   │       ├── MainActivity.java
│   │   │       ├── MangaParkApp.java
│   │   │       ├── CacheScheduler.java
│   │   │       └── ...
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   └── ...
└── build.gradle
```

## 🔧 Core Components

- **WebView Configuration**: Optimized settings for manga reading
- **Image Cache Manager**: Efficient caching system for offline access
- **Cache Scheduler**: Automated cache cleanup at midnight
- **Fullscreen Manager**: Enhanced reading experience

## 💡 Advanced Features

### Cache Management
- Automatic caching of viewed manga pages
- Intelligent cache cleanup system
- Configurable cache size limits

### Performance Optimizations
- Optimized WebView settings
- Efficient memory management
- Battery-friendly operations

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## ⭐ Support

If you find this project useful, please consider giving it a star on GitHub!
