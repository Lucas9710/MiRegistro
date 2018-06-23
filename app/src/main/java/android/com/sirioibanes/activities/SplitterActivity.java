package android.com.sirioibanes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import android.com.sirioibanes.R;

public class SplitterActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitter);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    startActivity(new Intent(SplitterActivity.this, RegisterActivity.class));
                }
            });

            findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    startActivity(new Intent(SplitterActivity.this, LoginActivity.class));
                }
            });
        } else {
            startActivity(new Intent(SplitterActivity.this, HomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }
}
