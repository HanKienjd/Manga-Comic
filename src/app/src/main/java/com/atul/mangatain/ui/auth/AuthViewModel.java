package com.atul.mangatain.ui.auth;

import static com.atul.mangatain.ui.auth.AuthViewModel.ViewEffect.*;

import androidx.lifecycle.ViewModel;

import com.atul.mangatain.util.SingleLiveEvent;

public class AuthViewModel extends ViewModel {
    SingleLiveEvent<ViewEffect> effect = new SingleLiveEvent<>();
    public void goToRegister() {
        effect.setValue(new GoToRegister());
    }

    public void goToLogin() {
        effect.setValue(new GoToLogin());
    }

    public void registerSuccess() {
        effect.setValue(new Finish());
    }

    public void loginSuccess() {
        effect.setValue(new Finish());
    }

    interface ViewEffect {
        class GoToRegister implements ViewEffect {

        }
        class GoToLogin implements ViewEffect {

        }

        class Finish implements ViewEffect {

        }
    }
}
