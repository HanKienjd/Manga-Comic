package com.atul.mangatain.ui.auth;

import static com.atul.mangatain.ui.auth.AuthViewModel.*;

import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.atul.mangatain.R;
import com.atul.mangatain.base.BaseActivity;
import com.atul.mangatain.databinding.ActivityAuthBinding;
import com.atul.mangatain.ui.auth.login.LoginFragment;
import com.atul.mangatain.ui.auth.register.RegisterFragment;

public class AuthActivity extends BaseActivity<ActivityAuthBinding, AuthViewModel> {

    private LoginFragment _loginFragment = null;
    private RegisterFragment _registerFragment = null;

    @Override
    protected ActivityAuthBinding provideBinding(LayoutInflater inflater) {
        return ActivityAuthBinding.inflate(inflater);
    }

    @Override
    protected void createView() {
        switchFragment(getLoginFragment());
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    private LoginFragment getLoginFragment() {
        if (_loginFragment == null) {
            _loginFragment = new LoginFragment();
        }
        return _loginFragment;
    }

    private RegisterFragment getRegisterFragment() {
        if (_registerFragment == null) {
            _registerFragment = new RegisterFragment();
        }
        return _registerFragment;
    }

    @Override
    protected void registerObserver() {
        super.registerObserver();
        viewModel.effect.observe(this, viewEffect -> {
            if (viewEffect instanceof ViewEffect.GoToRegister) {
                switchFragment(getRegisterFragment());
            } else if (viewEffect instanceof ViewEffect.GoToLogin) {
                switchFragment(getLoginFragment());
            } else if (viewEffect instanceof ViewEffect.Finish) {
                this.finish();
            }
        });
    }
}
