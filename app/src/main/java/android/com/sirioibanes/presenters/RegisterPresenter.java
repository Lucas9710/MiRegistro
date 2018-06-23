package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.User;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.RegisterView;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterPresenter {
    private final FirebaseAuth mAuth;
    private RegisterView mView;
    private final DatabaseReference myRef;

    public RegisterPresenter(@NonNull final RegisterView registerView) {
        mAuth = FirebaseAuth.getInstance();
        mView = registerView;
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_USERS);
    }

    public void registerUser(@NonNull final String name, @NonNull final String lastName, @NonNull final String email,
                             @NonNull final String password, @NonNull final String phone) {

        if (name.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()
                || phone.trim().isEmpty()) {
            mView.onEmptyError();
            return;
        }

        mView.showProgress();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        mView.onRegisterError();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (mView == null) {
                            return;
                        }

                        if (task.isSuccessful()) {
                            final String nickName = email.replace("@", "")
                                    .replace("-", "").replace("_", "")
                                    .replace(".", "")
                                    + new Random().nextInt(999);

                            final User user = new User(name, lastName, nickName, email, phone, null);
                            myRef.child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mView.onRegisterSuccess();
                                    AuthenticationManager.getInstance().onRegister(mView.getContext(), user);
                                }
                            });
                        } else {
                            mView.onRegisterError();
                        }
                    }
                });
    }

    public void attachView(RegisterView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
