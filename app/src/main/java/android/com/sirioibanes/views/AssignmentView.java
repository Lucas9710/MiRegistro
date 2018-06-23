package android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public interface AssignmentView {
    void showEmptyView();

    void showProgressView();

    void showAssignments(@NonNull String table,
                         @NonNull List<HashMap<String, String>> assignments);

    Context getContext();
}
