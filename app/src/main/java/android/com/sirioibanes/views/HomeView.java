package android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;
import java.util.List;

import android.com.sirioibanes.dtos.Event;

public interface HomeView {
    void showEvents(@NonNull List<Event> events);

    void showEmptyView();

    void showProgressView();

    Context getContext();
}
