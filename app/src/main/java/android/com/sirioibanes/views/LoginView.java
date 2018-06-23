package android.com.sirioibanes.views;

import android.content.Context;

public interface LoginView {
    void onEmptyFields();

    void onLogin();

    void onLoginError();

    Context getContext();

    void showProgress();
}
