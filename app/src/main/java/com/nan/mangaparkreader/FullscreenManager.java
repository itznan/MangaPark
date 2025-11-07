package com.nan.mangaparkreader;

import android.app.Activity;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.WebView;

public class FullscreenManager {

    public static void setupFullscreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
            WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
            WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
            WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(params);
        }
    }

    public static void applyFullscreenMode(View decorView, WebView webView) {
        if (decorView == null) return;

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = decorView.getWindowInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            decorView.setSystemUiVisibility(uiOptions);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setupDisplayCutoutHandling(decorView, webView);
        }
    }

    private static void setupDisplayCutoutHandling(View decorView, WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                DisplayCutout displayCutout = insets.getDisplayCutout();
                if (displayCutout != null) {
                    int left = displayCutout.getSafeInsetLeft();
                    int top = displayCutout.getSafeInsetTop();
                    int right = displayCutout.getSafeInsetRight();
                    int bottom = displayCutout.getSafeInsetBottom();

                    if (webView != null) {
                        webView.setPadding(left, top, right, bottom);
                    }
                }
                return insets;
            });
        }
    }
}
