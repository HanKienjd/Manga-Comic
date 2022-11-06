package com.atul.mangatain.ui.auth.register;

import static com.atul.mangatain.ui.auth.register.RegisterViewModel.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.atul.mangatain.R;
import com.atul.mangatain.base.BaseFragment;
import com.atul.mangatain.databinding.FragmentRegisterBinding;
import com.atul.mangatain.ui.auth.AuthViewModel;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {

    private AuthViewModel parentAuthViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
    }
    @Override
    protected FragmentRegisterBinding provideBinding(LayoutInflater inflater, ViewGroup container, Boolean attachToParent) {
        return FragmentRegisterBinding.inflate(inflater, container, attachToParent);
    }

    @Override
    protected void createView() {

    }

    @Override
    protected void registerObserver() {
        super.registerObserver();
        viewModel.effect.observe(this, effect -> {
            if (effect instanceof ViewEffect.ResourceMessage) {
                Toast.makeText(getActivity(), ((ViewEffect.ResourceMessage) effect).messageRes, Toast.LENGTH_SHORT).show();
            } else if (effect instanceof ViewEffect.Message) {
                Toast.makeText(getActivity(), ((ViewEffect.Message) effect).message, Toast.LENGTH_SHORT).show();
            } else if (effect instanceof ViewEffect.RegisterSuccess) {
                parentAuthViewModel.registerSuccess();
            }
        });
        viewModel.viewState.observe(this, state -> {
            if (state.loading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void setupViewClickListener() {
        super.setupViewClickListener();
        binding.submit.setOnClickListener(this);
        binding.tvLoginNow.setOnClickListener(this);
    }

    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case (R.id.submit):
                viewModel.submit(
                        binding.email.getText().toString(),
                        binding.password.getText().toString(),
                        binding.confirmPassword.getText().toString()
                );
                break;
            case (R.id.tvLoginNow):
                parentAuthViewModel.goToLogin();
                break;
        }
    }
}
