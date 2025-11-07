package com.nan.mangaparkreader;

import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ImageCacheManager {
    private static final String TAG = "ImageCacheManager";
    private final Context context;
    private final Map<String, Boolean> cachedUrls = new HashMap<>();

    public ImageCacheManager(Context context) {
        this.context = context;
    }

    public boolean isImage(String url) {
        return url.matches(".*\\.(png|jpg|jpeg|gif|webp|bmp|svg)(\\?.*)?$");
    }

    public WebResourceResponse loadFromCache(String url) {
        try {
            File file = new File(context.getCacheDir(), cacheKey(url));
            if (file.exists()) {
                String mime = URLConnection.guessContentTypeFromName(url);
                if (mime == null) mime = "image/*";
                return new WebResourceResponse(mime, "utf-8", new FileInputStream(file));
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFromCache failed", e);
        }
        return null;
    }

    public void cacheImageAsync(String url) {
        if (!cachedUrls.containsKey(url)) {
            cachedUrls.put(url, true);
            new Thread(() -> cacheImage(url)).start();
        }
    }

    private void cacheImage(String url) {
        try {
            URL u = new URL(url);
            InputStream in = (InputStream) u.getContent();
            File file = new File(context.getCacheDir(), cacheKey(url));
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[8 * 1024];
            int n;
            while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
            out.close();
            in.close();
        } catch (Exception e) {
            Log.e(TAG, "cacheImage failed", e);
        }
    }

    private String cacheKey(String url) {
        return String.valueOf(url.hashCode());
    }

    public void clearCache() {
        cachedUrls.clear();
    }

    public void cleanupOldCache() {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                File[] files = cacheDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cleaning up cache", e);
        }
    }
}
