# MangaPark Reader - Android Application

A lightweight and efficient Android application designed for reading manga from MangaPark, offering enhanced performance and offline capabilities.

## Key Features

* Fast and responsive WebView interface
* Smart image caching system for offline reading
* Automatic cache cleanup scheduled at midnight
* Fullscreen reading mode for an improved reading experience
* Pull-to-refresh functionality
* Optimized for performance and battery efficiency

## Technical Requirements

* Android Studio or Command Line Tools
* JDK 17 or later
* Android SDK 35 (Android 14)
* Gradle 8.0 or later

## Quick Start

### Build from Source

1. **Clone the Repository**

   ```bash
   git clone https://github.com/itznan/mangapark.git
   cd MangaPark
   ```

2. **Build the APK**

   ```bash
   ./gradlew assembleDebug
   ```

   The generated APK will be located at:

   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

3. **Install on Device**

   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Project Architecture

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

## Core Components

* **WebView Configuration**: Custom settings optimized for manga reading
* **Image Cache Manager**: Efficient caching mechanism for offline page access
* **Cache Scheduler**: Automated cleanup system executed at midnight
* **Fullscreen Manager**: Controls immersive reading mode

## Advanced Features

### Cache Management

* Automatic caching of viewed manga pages
* Intelligent cleanup routine to prevent storage bloat
* Adjustable cache limits for different device profiles

### Performance Enhancements

* Optimized WebView parameters
* Efficient memory usage
* Reduced battery overhead during continuous reading

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/YourFeatureName`)
3. Commit your changes (`git commit -m "Describe your changes"`)
4. Push the branch (`git push origin feature/YourFeatureName`)
5. Open a Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for full details.

## Support

If this project benefits you, consider starring the repository on GitHub.
