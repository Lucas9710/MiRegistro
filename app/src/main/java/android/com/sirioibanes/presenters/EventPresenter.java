package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.EventView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;

public class EventPresenter {

    public static final String ASSISTANCE_CONFIRM = "confirmado";
    public static final String ASSISTANCE_MAYBE = "quizas";
    public static final String ASSISTANCE_NEGATIVE = "cancelado";

    private final String mEventKey;
    private EventView mView;
    private final DatabaseReference myRef;
    private Event mEvent;

    public EventPresenter(@NonNull final String eventKey) {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_EVENTS);
        mEventKey = eventKey;
    }

    public EventPresenter(final Event event) {
        this(event.key);
        mEvent = event;
    }

    private void getEvents(final String eventKey) {
        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                if (mView != null) {
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final Event event = new Event(postSnapshot.getKey(),
                                (AbstractMap<String, Object>) postSnapshot.getValue());

                        if (postSnapshot.getKey().equalsIgnoreCase(eventKey)) {
                            mView.showEvent(event);
                            mView.onAssistanceConfirmed(getAssistanceStatus(event));
                        }
                    }
                }

            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addListenerForSingleValueEvent(eventListener);
    }

    public void confirmAssistance(@NonNull final String status) {
        myRef.child(mEventKey).child("invitados").child(AuthenticationManager.getInstance()
                .getUser(mView.getContext()).getNickname()).setValue(status)
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onAssistanceError();
            }
        });

        mView.onAssistanceConfirmed(status);
    }

    public void onAttachView(@NonNull final EventView view) {
        mView = view;

        if (mEvent == null && mEventKey != null) {
            getEvents(mEventKey);
        }

        if (mEvent != null) {
            mView.onAssistanceConfirmed(getAssistanceStatus(mEvent));
        }
    }

    @Nullable
    private String getAssistanceStatus(final Event event) {
        if (event.invitados == null) {
            return null;
        }

        return event.invitados.get(AuthenticationManager.getInstance()
                .getUser(mView.getContext()).getNickname());
    }

    public void detachView() {
        mView = null;
    }
}
