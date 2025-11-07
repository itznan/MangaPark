package com.nan.mangaparkreader;

import android.os.Handler;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CacheScheduler {
    private final ImageCacheManager cacheManager;
    private final Handler handler;

    public CacheScheduler(ImageCacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.handler = new Handler();
    }

    public void scheduleCleanup() {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= now) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        long timeUntilMidnight = calendar.getTimeInMillis() - now;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cacheManager.cleanupOldCache();
                handler.postDelayed(this, TimeUnit.HOURS.toMillis(24));
            }
        }, timeUntilMidnight);
    }

    public void cancelSchedule() {
        handler.removeCallbacksAndMessages(null);
    }
}
