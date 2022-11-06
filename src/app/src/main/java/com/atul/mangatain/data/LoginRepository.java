package com.atul.mangatain.data;

import android.database.Observable;

import androidx.annotation.NonNull;

import com.atul.mangatain.data.model.LoggedInUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // private constructor : singleton access

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void logout() {
        dataSource.logout();
    }

    public void register(String email, String password, ResultListener resultListener) {
        dataSource.registerWithFirebase(email, password, resultListener);
    }

    public void login(String email, String password, ResultListener resultListener) {
        dataSource.login(email, password, resultListener);
    }
}