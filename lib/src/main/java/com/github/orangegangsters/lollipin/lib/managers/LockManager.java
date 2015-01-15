package com.github.orangegangsters.lollipin.lib.managers;

import android.content.Context;

/**
 * Allows to handle the {@link com.github.orangegangsters.lollipin.lib.managers.AppLock} from within
 * the actual app calling the library.
 * You must get this static instance by calling {@link #getInstance()}
 */
public class LockManager<T extends AppLockActivity> {

    /**
     * The static singleton instance
     */
    private volatile static LockManager mInstance;
    /**
     * The static singleton instance of {@link com.github.orangegangsters.lollipin.lib.managers.AppLock}
     */
    private static AppLock mAppLocker;

    /**
     * Used to retrieve the static instance
     */
    public static LockManager getInstance() {
        synchronized (LockManager.class) {
            if (mInstance == null) {
                mInstance = new LockManager<>();
            }
        }
        return mInstance;
    }

    /**
     * You must call that into your custom {@link android.app.Application} to enable the
     * {@link com.github.orangegangsters.lollipin.lib.PinActivity}
     */
    public void enableAppLock(Context context, Class<T> activityClass) {
        if (mAppLocker == null) {
            mAppLocker = new AppLockImpl<>(context, activityClass);
        }
        mAppLocker.enable();
    }

    /**
     * Tells the app if the {@link com.github.orangegangsters.lollipin.lib.managers.AppLock} is enabled or not
     */
    public boolean isAppLockEnabled() {
        if (mAppLocker == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Disables the app lock by calling {@link AppLock#disable()}
     */
    public void disableAppLock() {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
    }

    /**
     * Disables the previous app lock and set a new one
     */
    public void setAppLock(AppLock appLocker) {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = appLocker;
    }

    /**
     * Get the {@link AppLock}. Used for defining custom timeouts etc...
     */
    public AppLock getAppLock() {
        return mAppLocker;
    }
}
