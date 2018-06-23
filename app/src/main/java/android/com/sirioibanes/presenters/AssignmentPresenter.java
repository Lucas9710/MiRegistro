package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.AssignmentView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by federicobustosfierro on 2/22/18.
 */

public class AssignmentPresenter {

    private final String mEventKey;
    private AssignmentView mView;
    private final DatabaseReference myRef;

    public AssignmentPresenter(@NonNull final String eventKey) {
        mEventKey = eventKey;
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_ASSIGNMENTS);
    }

    private void getAssignment() {

        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (final DataSnapshot postSnapshot : dataSnapshot.child(mEventKey).getChildren()) {
                    final List<HashMap<String, String>> list =
                            ((List<HashMap<String, String>>) postSnapshot.getValue());

                    if (list == null || list.isEmpty()) {
                        mView.showEmptyView();
                    } else {
                        final List<HashMap<String, String>> mapList = new ArrayList<>(list);

                        for (HashMap<String, String> map : list) {
                            if (map.get("nickname").equals(AuthenticationManager.getInstance().getUser(mView.getContext()).getNickname())) {
                                mapList.remove(map);
                                mView.showAssignments(postSnapshot.getKey(), mapList);
                                return;
                            }
                        }

                        mView.showEmptyView();
                    }
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);
    }

    public void onAttachView(@NonNull final AssignmentView view) {
        getAssignment();
        mView = view;
    }
}
