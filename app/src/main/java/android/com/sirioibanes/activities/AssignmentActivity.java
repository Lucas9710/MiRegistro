package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.TableAdapter;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.AssignmentPresenter;
import android.com.sirioibanes.views.AssignmentView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public class AssignmentActivity extends AbstractActivity implements AssignmentView {

    private static final String EXTRA_EVENT = "extra-event";
    private static final int STATE_PROGRESS = 0;
    private static final int STATE_LIST = 1;
    private static final int STATE_EMPTY = 2;
    private AssignmentPresenter mPresenter;
    private Event mEvent;
    private final TableAdapter mAdapter = new TableAdapter();


    public static Intent getIntent(@NonNull final Context context, @NonNull final Event event) {
        final Intent intent = new Intent(context, AssignmentActivity.class);
        intent.putExtra(EXTRA_EVENT, event);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        if (getIntent().getExtras() == null || getIntent().getSerializableExtra(EXTRA_EVENT) == null) {
            throw new AssertionError("Use the activity's static factory method");
        }

        mEvent = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentActivity.this));

        mPresenter = new AssignmentPresenter(mEvent.key);
    }

    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void showEmptyView() {
        setState(STATE_EMPTY);
    }

    @Override
    public void showProgressView() {
        setState(STATE_PROGRESS);
    }

    @Override
    public void showAssignments(@NonNull final String table,
                                @NonNull final List<HashMap<String, String>> assignments) {

        String tableNumber = table;
        tableNumber = tableNumber.replace("Mesa ", "");
        ((TextView) findViewById(R.id.title)).setText(String.format("Tu mesa es la %1$s", tableNumber));
        setState(STATE_LIST);
        mAdapter.setItems(assignments);
    }


    public Context getContext() {
        return AssignmentActivity.this;
    }


    @Override
    protected boolean shouldValidate() {
        return false;
    }


    private void setState(final int state) {
        ((ViewFlipper) findViewById(R.id.viewFlipper)).setDisplayedChild(state);
    }

}
