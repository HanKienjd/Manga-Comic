package com.atul.mangatain.ui.auth.register;

import android.util.Patterns;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.R;
import com.atul.mangatain.data.LoginDataSource;
import com.atul.mangatain.data.LoginRepository;
import com.atul.mangatain.data.Result;
import com.atul.mangatain.util.SingleLiveEvent;

public class RegisterViewModel extends ViewModel {
    SingleLiveEvent<ViewEffect> effect = new SingleLiveEvent<>();
    private ViewState currentState = new ViewState();
    MutableLiveData<ViewState> viewState = new MutableLiveData<>(currentState);

    LoginRepository repository = LoginRepository.getInstance(LoginDataSource.getInstance());

    public void submit(String email, String password, String confirmPassword) {
        currentState.loading = true;
        viewState.postValue(currentState);
        String formattedEmail = email.trim().toLowerCase();
        if (!Patterns.EMAIL_ADDRESS.matcher(formattedEmail).matches()) {
            effect.setValue(new ViewEffect.ResourceMessage(R.string.invalid_email));
            currentState.loading = false;
            viewState.postValue(currentState);
            return;
        }
        if (!password.equals(confirmPassword)) {
            effect.setValue(new ViewEffect.ResourceMessage(R.string.passwords_do_not_match));
            currentState.loading = false;
            viewState.postValue(currentState);
            return;
        }
        registerAccount(email, password);
    }

    private void registerAccount(String email, String password) {
        repository.register(email, password, result -> {
            currentState.loading = false;
            viewState.postValue(currentState);
            if (result instanceof Result.Success) {
                effect.setValue(new ViewEffect.RegisterSuccess());
            } else if (result instanceof Result.Error) {
                effect.setValue(new ViewEffect.Message(((Result.Error) result).getError().getLocalizedMessage()));
            }
        });
    }

    class ViewState {
        boolean loading = false;

        public ViewState() {

        }
    }

    interface ViewEffect {
        class ResourceMessage implements ViewEffect {

            public int messageRes;

            public ResourceMessage(@StringRes Integer messageRes) {
                this.messageRes = messageRes;
            }
        }

        class Message implements ViewEffect {

            public String message;

            public Message(String message) {
                this.message = message;
            }
        }

        class RegisterSuccess implements ViewEffect {

        }
    }
}
