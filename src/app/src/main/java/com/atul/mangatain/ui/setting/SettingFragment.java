package com.atul.mangatain.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.MTPreferences;
import com.atul.mangatain.R;
import com.atul.mangatain.base.BaseFragment;
import com.atul.mangatain.databinding.FragmentSettingBinding;
import com.atul.mangatain.ui.auth.AuthActivity;
import com.atul.mangatain.ui.setting.adapter.AccentAdapter;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> {
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected FragmentSettingBinding provideBinding(LayoutInflater inflater, ViewGroup container, Boolean attachToParent) {
        return FragmentSettingBinding.inflate(inflater, container, attachToParent);
    }

    @Override
    protected void createView() {
        setCurrentThemeMode();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        binding.accentView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.accentView.setAdapter(new AccentAdapter(getActivity()));
    }

    @Override
    protected void registerObserver() {
        super.registerObserver();
        viewModel.viewStateLiveData.observe(this, this::handleLoginState);
    }

    private void handleLoginState(SettingViewModel.ViewState viewState) {
        if (viewState.loggedIn) {
            // show profile
            binding.llProfile.setVisibility(View.VISIBLE);
            binding.tvEmail.setText(viewState.email);
            binding.login.setVisibility(View.GONE);
        } else {
            // show login button
            binding.llProfile.setVisibility(View.GONE);
            binding.login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setupViewClickListener() {
        super.setupViewClickListener();

        binding.llProfile.setOnClickListener(this);
        binding.login.setOnClickListener(this);
        binding.accentOption.setOnClickListener(this);
        binding.themeModeOption.setOnClickListener(this);
        binding.githubOption.setOnClickListener(this);

        binding.nightChip.setOnClickListener(this);
        binding.lightChip.setOnClickListener(this);
        binding.autoChip.setOnClickListener(this);
    }

    private void setCurrentThemeMode() {
        int mode = MTPreferences.getThemeMode(requireActivity().getApplicationContext());

        if (mode == AppCompatDelegate.MODE_NIGHT_NO)
            binding.currentThemeMode.setImageResource(R.drawable.ic_theme_mode_light);

        else if (mode == AppCompatDelegate.MODE_NIGHT_YES)
            binding.currentThemeMode.setImageResource(R.drawable.ic_theme_mode_night);

        else
            binding.currentThemeMode.setImageResource(R.drawable.ic_theme_mode_auto);
    }

    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        int id = view.getId();

        switch (id) {
            case (R.id.accent_option):
                int visibility = (binding.accentView.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
                binding.accentView.setVisibility(visibility);
                break;
            case (R.id.github_option):
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(MTConstants.GITHUB_REPO_URL)
                ));
                break;
            case (R.id.theme_mode_option):
                int mode = binding.chipLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                binding.chipLayout.setVisibility(mode);
                break;
            case (R.id.night_chip):
                selectTheme(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case (R.id.light_chip):
                selectTheme(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case (R.id.auto_chip):
                selectTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case (R.id.login):
                goToLoginScreen();
                break;
            case (R.id.llProfile):
                viewModel.signOut();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
    }

    private void selectTheme(int theme) {
        AppCompatDelegate.setDefaultNightMode(theme);
        MTPreferences.storeThemeMode(requireActivity().getApplicationContext(), theme);
    }
}