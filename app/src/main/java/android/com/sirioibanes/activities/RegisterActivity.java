package android.com.sirioibanes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import android.com.sirioibanes.R;
import android.com.sirioibanes.presenters.RegisterPresenter;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.com.sirioibanes.views.RegisterView;
import android.widget.ViewFlipper;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private static final int STATE_PROGRESS = 1;
    private static final int STATE_REGULAR = 0;
    private RegisterPresenter mPresenter;
    private ViewFlipper mViewFlipper;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPresenter = new RegisterPresenter(this);

        final AppCompatEditText emailView = findViewById(R.id.fieldEmail);
        final AppCompatEditText nameView = findViewById(R.id.fieldName);
        final AppCompatEditText passwordView = findViewById(R.id.fieldPassword);
        final AppCompatEditText lastNameView = findViewById(R.id.fieldLastName);
        final AppCompatEditText phoneView = findViewById(R.id.fieldPhone);
        mViewFlipper = findViewById(R.id.viewFlipper);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.registerUser(nameView.getText().toString(),
                        lastNameView.getText().toString(),
                        emailView.getText().toString(), passwordView.getText().toString(),
                        phoneView.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }

    @Override
    public void onRegisterError() {
        mViewFlipper.setDisplayedChild(STATE_REGULAR);
        FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_signup));
    }

    @Override
    public void onRegisterSuccess() {
        startActivity(new Intent(this, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public void onEmptyError() {
        FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_empty_fields));
    }

    @Override
    public Context getContext() {
        return RegisterActivity.this;
    }

    @Override
    public void showProgress() {
        mViewFlipper.setDisplayedChild(STATE_PROGRESS);
    }
}
