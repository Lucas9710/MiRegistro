package android.com.sirioibanes.views;

import android.com.sirioibanes.dtos.Event;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.AbstractMap;

public interface EventView {
    void showEvent(Event event);

    Context getContext();

    void onAssistanceConfirmed(@Nullable String status);

    void onAssistanceError();
}
