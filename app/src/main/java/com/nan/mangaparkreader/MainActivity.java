package com.nan.mangaparkreader;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageCacheManager cacheManager;
    private CacheScheduler cacheScheduler;
    private static final String TARGET_URL = "https://mangapark.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullscreenManager.setupFullscreen(this);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeManagers();
        setupWebView();
        setupSwipeRefresh();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void initializeViews() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void initializeManagers() {
        cacheManager = new ImageCacheManager(this);
        cacheScheduler = new CacheScheduler(cacheManager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            FullscreenManager.applyFullscreenMode(decorView, webView);

            if (webView != null && webView.getUrl() == null) {
                webView.loadUrl(TARGET_URL);
            }
        }
    }

    private void setupWebView() {
        WebViewConfigurator.configure(webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MangaParkWebViewClient(cacheManager, progressBar, swipeRefreshLayout));
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> webView.reload());
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.WHITE);

        webView.setOnScrollChangeListener((View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) ->
                swipeRefreshLayout.setEnabled(scrollY == 0)
        );
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cacheScheduler.scheduleCleanup();
    }

    private void clearAllCache() {
        // Clear WebView cache
        webView.clearCache(true);
        
        // Clear the image cache
        if (cacheManager != null) {
            cacheManager.clearCache();
        }
        
        // Show a quick message to confirm cache cleared
        com.google.android.material.snackbar.Snackbar.make(
            findViewById(android.R.id.content),
            "Cache cleared",
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        if (cacheScheduler != null) {
            cacheScheduler.cancelSchedule();
        }
        super.onDestroy();
    }
}
