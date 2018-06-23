package android.com.sirioibanes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import android.com.sirioibanes.dtos.User;

class SharedPreferencesUtils {
    private final SharedPreferences mPrefs;
    private static SharedPreferencesUtils mInstance;

    public SharedPreferencesUtils(@NonNull final Context context) {
        mPrefs = context.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(@NonNull final Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesUtils(context);
        }

        return mInstance;
    }

    public <T> void save(final String key, final T value) {
        mPrefs.edit().putString(key, new Gson().toJson(value)).apply();
    }

    public <T> T read(final String key, final Class<T> t) {
        return new Gson().fromJson(mPrefs.getString(key, null), t);
    }
}
