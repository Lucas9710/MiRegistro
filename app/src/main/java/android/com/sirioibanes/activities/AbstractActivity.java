package android.com.sirioibanes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.com.sirioibanes.utils.AuthenticationManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public abstract class AbstractActivity extends AppCompatActivity {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldValidate()) {
            if (!AuthenticationManager.getInstance().isLoggedIn()) {
                startActivity(new Intent(this, SplitterActivity.class));
            }
        }
    }

    protected abstract boolean shouldValidate();
}
