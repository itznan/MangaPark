package com.nan.mangaparkreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

public class MangaParkWebViewClient extends WebViewClient {
    private final ImageCacheManager cacheManager;
    private final ProgressBar progressBar;
    private final SwipeRefreshLayout swipeRefreshLayout;

    public MangaParkWebViewClient(ImageCacheManager cacheManager,
                                   ProgressBar progressBar,
                                   SwipeRefreshLayout swipeRefreshLayout) {
        this.cacheManager = cacheManager;
        this.progressBar = progressBar;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (cacheManager.isImage(url)) {
            WebResourceResponse cached = cacheManager.loadFromCache(url);
            if (cached != null) return cached;
            cacheManager.cacheImageAsync(url);
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setVisibility(View.VISIBLE);
        cacheManager.clearCache();
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        super.onPageFinished(view, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
        if (req.isForMainFrame()) {
            progressBar.setVisibility(View.GONE);
            showError(view, "Error: " + err.getDescription());
        }
        super.onReceivedError(view, req, err);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (url.contains("patreon.com") || url.contains("discord.gg") ||
                url.startsWith("market://") || (url.startsWith("http") && !url.contains("mangapark.io"))) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(i);
            return true;
        }
        return false;
    }

    private void showError(WebView view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }
}
