package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.HomeView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class HomePresenter {

    private HomeView mView;
    private final DatabaseReference myRef;

    public HomePresenter() {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_EVENTS);
    }

    private void getEvents() {
        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (mView != null) {
                    final List<Event> events = new ArrayList<>();

                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final AbstractMap<String, Object> event = (AbstractMap<String, Object>) postSnapshot.getValue();
                        final AbstractMap<String, Boolean> userEvents = AuthenticationManager.getInstance()
                                .getUser(mView.getContext()).getEventos();

                        if (userEvents != null && userEvents.containsKey(postSnapshot.getKey())
                                && AuthenticationManager.getInstance().getUser(mView.getContext())
                                .getEventos().get(postSnapshot.getKey()))
                            events.add(new Event(postSnapshot.getKey(), event));
                    }

                    if (events.isEmpty()) {
                        mView.showEmptyView();
                    } else {
                        mView.showEvents(events);
                    }
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addListenerForSingleValueEvent(eventListener);
    }

    public void onAttachView(@NonNull final HomeView view) {
        getEvents();
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
