package android.com.sirioibanes.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import android.com.sirioibanes.R;

public class FeedbackUtils {

    public static void displaySnackbarError(@NonNull final View view, final String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getContext().getResources()
                .getColor(R.color.red));

        snackbar.show();
    }

    public static void displaySnackbarSuccess(@NonNull final View view, final String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getContext().getResources()
                .getColor(R.color.green));

        snackbar.show();
    }
}
