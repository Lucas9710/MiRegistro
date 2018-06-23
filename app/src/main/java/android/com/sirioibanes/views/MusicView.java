package android.com.sirioibanes.views;

import android.com.sirioibanes.dtos.Song;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public interface MusicView {
    Context getContext();

    void showSongs(@NonNull final List<Song> songs);

    void onError();

    void onEmptyResults();
}
