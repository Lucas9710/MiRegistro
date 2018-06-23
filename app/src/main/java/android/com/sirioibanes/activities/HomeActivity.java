package android.com.sirioibanes.activities;

import android.app.AlertDialog;
import android.com.sirioibanes.Manifest;
import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.EventsAdapter;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.HomePresenter;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.HomeView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewFlipper;

import java.util.List;

import static com.google.gson.internal.$Gson$Types.arrayOf;

public class HomeActivity extends AbstractActivity implements HomeView, EventsAdapter.EventClickListener {

    private static final int STATE_PROGRESS = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_LIST = 2;
    private EventsAdapter mEventsAdapter;
    private ViewFlipper mViewFlipper;
    private HomePresenter mPresenter;



    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter();
        mEventsAdapter = new EventsAdapter(this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mViewFlipper = findViewById(R.id.viewFlipper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mEventsAdapter);

        findViewById(R.id.buttonScanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                scannerButtonPressed();
            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                askLogout();
            }
        });
    }

    private void scannerButtonPressed () {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 100);
        } else {
            startActivity(new Intent(HomeActivity.this, ScannerActivity.class));

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(HomeActivity.this, ScannerActivity.class));
        } else {
            startActivity(new Intent(HomeActivity.this, PermissionsActivity.class));
        }
    }

    private void askLogout() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Cerrar sesión")
                .setMessage("¿Está seguro de que desea cerrar su sesión?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        doLogout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

    }

    private void doLogout() {
        AuthenticationManager.getInstance().logout(HomeActivity.this);
        startActivity(new Intent(HomeActivity.this, SplitterActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

        @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    public void showEvents(@NonNull final List<Event> events) {
        setState(STATE_LIST);
        mEventsAdapter.setItems(events);
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
    public Context getContext() {
        return HomeActivity.this;
    }

    private void setState(final int state) {
        mViewFlipper.setDisplayedChild(state);
    }

    @Override
    public void onClick(@NonNull final Event event) {
        startActivity(EventActivity.getIntent(HomeActivity.this, event));
    }
}
