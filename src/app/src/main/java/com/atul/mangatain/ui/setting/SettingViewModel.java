package com.atul.mangatain.ui.setting;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.data.LoginDataSource;
import com.atul.mangatain.data.LoginRepository;
import com.google.firebase.auth.FirebaseAuth;

public class SettingViewModel extends ViewModel {

    private final ViewState currentState = new ViewState();
    MutableLiveData<ViewState> viewStateLiveData = new MutableLiveData<>(currentState);

    private LoginRepository loginRepository = LoginRepository.getInstance(LoginDataSource.getInstance());

    public SettingViewModel() {
        registerAuthState();
    }

    private void registerAuthState() {
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                currentState.loggedIn = true;
                currentState.email = firebaseAuth.getCurrentUser().getEmail();
            } else {
                currentState.loggedIn = false;
            }
            viewStateLiveData.postValue(currentState);
        });
    }

    public void signOut() {
        loginRepository.logout();
    }

    static class ViewState {
        String email;
        Boolean loggedIn;

        public ViewState() {
            email = null;
            loggedIn = false;
        }

        public ViewState(String email, Boolean loggedIn) {
            this.email = email;
            this.loggedIn = loggedIn;
        }
    }
}
