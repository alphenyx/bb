package com.google.samples.quickstart.analytics;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        try {
            if (mTracker == null) {
                GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
                // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
                mTracker = analytics.newTracker("UA-88454803-1");
            }
        }catch(Exception e)
        {}
        return mTracker;
    }
}