package android.com.sirioibanes.views;

import android.content.Context;

public interface RegisterView {
    void onRegisterError();

    void onRegisterSuccess();

    void onEmptyError();

    Context getContext();

    void showProgress();
}
