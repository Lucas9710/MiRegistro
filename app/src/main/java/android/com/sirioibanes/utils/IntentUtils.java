package android.com.sirioibanes.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class IntentUtils {

    public static boolean isActivityAvailable(@NonNull final Context context, @NonNull final Intent intent) {
        return !context.getPackageManager().queryIntentActivities(intent, 0).isEmpty();
    }
}
