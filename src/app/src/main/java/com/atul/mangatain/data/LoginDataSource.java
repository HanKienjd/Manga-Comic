package com.atul.mangatain.data;

import androidx.annotation.NonNull;

import com.atul.mangatain.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    static LoginDataSource instance;

    public static LoginDataSource getInstance() {
        if (instance == null) {
            instance = new LoginDataSource();
        }
        return instance;
    }

    public void login(String email, String password, ResultListener onResultListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = task.getResult().getUser();
                Result.Success<FirebaseUser> result = new Result.Success<>(user);
                onResultListener.onResultListener(result);
            } else {
                Result.Error error = new Result.Error(task.getException());
                onResultListener.onResultListener(error);
            }
        });
    }

    public void registerWithFirebase(String email, String password, ResultListener onResultListener) {
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        Result.Success<FirebaseUser> result = new Result.Success<>(user);
                        onResultListener.onResultListener(result);
                    } else {
                        Result.Error error = new Result.Error(task.getException());
                        onResultListener.onResultListener(error);
                    }
                });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }


}