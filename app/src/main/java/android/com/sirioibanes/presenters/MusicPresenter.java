package android.com.sirioibanes.presenters;

import android.com.sirioibanes.adapters.holders.SongViewHolder;
import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Song;
import android.com.sirioibanes.views.MusicView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MusicPresenter {

    private final DatabaseReference mRef;
    private final String mEventKey;
    private MusicView mView;
    private final ArrayList<Song> mSongs = new ArrayList<>();

    public MusicPresenter(@NonNull final String eventKey) {
        mEventKey = eventKey;
        mRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_MUSIC);
    }

    private void getSongs() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (mView == null) {
                    return;
                }

                final List<HashMap<String, Object>> list =
                        ((List<HashMap<String, Object>>) dataSnapshot.child(mEventKey).getValue());

                if (list == null) {
                    mView.onEmptyResults();
                    return;
                }

                mSongs.clear();

                for (int i = 0; i < list.size(); i++) {
                    final Song song = new Song(list.get(i));
                    mSongs.add(song);
                }

                if (!mSongs.isEmpty()) {
                    Collections.sort(mSongs, new Comparator<Song>() {
                        @Override
                        public int compare(Song o1, Song o2) {
                            return (o1.getVotos() < o2.getVotos()) ? 1 : ((o1.getVotos() == o2.getVotos()) ? 0 : -1);
                        }
                    });

                    mView.showSongs(mSongs);
                } else {
                    mView.onEmptyResults();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                mView.onError();
            }
        });
    }

    public void newSong(@NonNull final Song song) {
        mSongs.add(song);
        mRef.child(mEventKey).setValue(mSongs);
    }

    public void vote(@NonNull final Song song, final @SongViewHolder.VoteType int type) {
        song.vote(type);
        mSongs.set(mSongs.indexOf(song), song);
        mRef.child(mEventKey).setValue(mSongs);
    }

    public void onAttachView(@NonNull final MusicView musicView) {
        mView = musicView;
        getSongs();
    }

    public void onDetachView() {
        mView = null;
    }
}
