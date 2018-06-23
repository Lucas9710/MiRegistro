package android.com.sirioibanes.activities;

import android.app.AlertDialog;
import android.com.sirioibanes.R;
import android.com.sirioibanes.presenters.LoginPresenter;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.com.sirioibanes.views.LoginView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ViewFlipper;

public class LoginActivity extends AbstractActivity implements LoginView {

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);
        final AppCompatEditText emailView = findViewById(R.id.fieldEmail);
        final AppCompatEditText passwordView = findViewById(R.id.fieldPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.login(emailView.getText().toString(), passwordView.getText().toString());
            }
        });

        findViewById(R.id.buttonForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (emailView.getText().toString().isEmpty()) {
                    FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView),
                             "Completa tu email y vuelve a intentarlo");
                } else {
                    AuthenticationManager.getInstance().recoverPassword(emailView.getText().toString());
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("¡Mail enviado!")
                            .setMessage("Te hemos enviado un mail de recuperación a tu casilla")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
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
    public void onEmptyFields() {
        FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_empty_fields));
    }

    @Override
    public void onLogin() {
        startActivity(new Intent(this, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public void onLoginError() {
        showRegularLayout();
        FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_login));
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void showProgress() {
        setLayoutState(1);
    }

    private void showRegularLayout() {
        setLayoutState(0);
    }

    private void setLayoutState(final int state) {
        ((ViewFlipper) findViewById(R.id.viewFlipper)).setDisplayedChild(state);
    }
}
