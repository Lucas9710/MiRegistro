package android.com.sirioibanes.views;

import android.com.sirioibanes.dtos.Event;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;

public interface ScannerView {
    void showEvent(@NonNull Event event);

    void onInvalidEvent();

    Context getContext();

    void onEventAssociationError();
}
