package com.nan.mangaparkreader;

import android.app.Application;
import android.webkit.WebView;

public class MangaParkApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable WebView debugging automatically in debug builds
        if ((getApplicationInfo().flags & android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Initialize any global resources here
        initializeApp();
    }

    private void initializeApp() {
        // Example: preload resources, analytics setup, etc.
    }
}
