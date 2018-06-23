package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.ScannerView;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.AbstractMap;

public class ScannerPresenter {

    private ScannerView mView;

    public ScannerPresenter() {
    }

    public void onAttachView(@NonNull final ScannerView view) {
        mView = view;
    }

    public void getEvent(@NonNull final String code) {

        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_CODES).child(getNormalizedCode(code));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String eventKey = (String) dataSnapshot.getValue();
                if (eventKey != null) {
                    getEventPostCodeRedeem(eventKey);
                } else {
                    mView.onInvalidEvent();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        });

    }

    public void getEventPostCodeRedeem(@NonNull final String eventKey) {

        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_EVENTS).child(getNormalizedCode(eventKey));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final AbstractMap<String, Object> event = (AbstractMap<String, Object>) dataSnapshot.getValue();
                if (event == null) {
                    mView.onInvalidEvent();
                } else {
                    mView.showEvent(new Event(eventKey, event));
                    associateEvent(eventKey);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        });

    }

    private void associateEvent(@NonNull final String eventKey) {
        final DatabaseReference userTableRef = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_USERS);

        final DatabaseReference eventTableRef = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_EVENTS);

        userTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot database) {
                for (DataSnapshot snapshot : database.getChildren()) {
                    if (snapshot.child("nickname").getValue().equals(AuthenticationManager.getInstance()
                            .getUser(mView.getContext()).getNickname())) {
                        userTableRef.child(snapshot.getKey()).child("eventos").child(eventKey).setValue(true)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<Void> task) {

                                        eventTableRef.child(eventKey).child("invitados").child(AuthenticationManager.getInstance()
                                                .getUser(mView.getContext()).getNickname()).setValue("indeterminado")
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        mView.onEventAssociationError();
                                                    }
                                                });

                                        AuthenticationManager.getInstance().associateEvent(mView.getContext(), eventKey);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull final Exception e) {
                                mView.onEventAssociationError();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                mView.onEventAssociationError();
            }
        });
    }

    private String getNormalizedCode(final String code) {
        return Normalizer.normalize(code, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z]", "");
    }
}
