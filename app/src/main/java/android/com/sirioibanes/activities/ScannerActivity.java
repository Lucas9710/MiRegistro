package android.com.sirioibanes.activities;

import android.Manifest;
import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.ScannerPresenter;
import android.com.sirioibanes.views.ScannerView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.AbstractMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AbstractActivity implements ZXingScannerView.ResultHandler,
        ScannerView {

    private static final String PARAM_EVENT = "evento";
    private static final int CAMERA_REQUEST_CODE = 100;
    private ZXingScannerView mScannerView;
    private ScannerPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        mPresenter = new ScannerPresenter();

        mScannerView = findViewById(R.id.scannerView);
        mScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        findViewById(R.id.buttonScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String key = ((AppCompatEditText) findViewById(R.id.fieldCode))
                        .getText().toString().trim();

                if (key.isEmpty()) {
                    onInvalidEvent();
                } else {
                    mPresenter.getEvent(key);
                }
            }
        });

        AppCompatEditText editText = (AppCompatEditText) findViewById(R.id.fieldCode);
        if (ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            mScannerView.getLayoutParams().height = 20;
            editText.requestFocus();
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            final float scale = getResources().getDisplayMetrics().density;
            int size  = (int) (250 * scale);
            mScannerView.getLayoutParams().height = size;
            editText.clearFocus();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AppCompatEditText editText = (AppCompatEditText) findViewById(R.id.fieldCode);
        final float scale = getResources().getDisplayMetrics().density;
        int size  = (int) (250 * scale);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mScannerView.startCamera();
            mScannerView.getLayoutParams().height = size;
            editText.clearFocus();
        } else {
            mScannerView.getLayoutParams().height = 20;
            editText.requestFocus();
        }
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCameraPreview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void handleResult(final Result result) {
        final Uri uri = Uri.parse(result.getText());
        final String eventKey = uri.getQueryParameter(PARAM_EVENT) == null
                ? uri.getLastPathSegment() : uri.getQueryParameter(PARAM_EVENT);

        // Hacemos esto porque enviaron invitaciones con una URL inválida en su primer evento XD
        mPresenter.getEvent(eventKey.equals("barGy7") ? "valenedi" : eventKey);
    }

    @Override
    public void showEvent(@NonNull final Event event) {
        startActivity(EventActivity.getIntent(ScannerActivity.this, event));
    }

    @Override
    public void onInvalidEvent() {
        Toast.makeText(ScannerActivity.this, "El código de evento es inválido",
                Toast.LENGTH_LONG).show();
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public Context getContext() {
        return ScannerActivity.this;
    }

    @Override
    public void onEventAssociationError() {
        Toast.makeText(ScannerActivity.this, "No hemos podido asociarte al evento",
                Toast.LENGTH_LONG).show();
    }
}
