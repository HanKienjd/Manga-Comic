package com.atul.mangatain.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.atul.mangatain.databinding.FragmentLoginBinding;
import com.atul.mangatain.databinding.FragmentUserBinding;
import com.atul.mangatain.ui.login.LoginViewModel;
import com.atul.mangatain.ui.login.LoginViewModelFactory;

public class User extends Fragment {

    private @NonNull FragmentUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView profileImage = binding.imageviewProfile;
        final ImageView nameImage = binding.icName;
        final TextView nameTextview = binding.textviewName;
        final ImageView birthImage = binding.icBirthday;
        final TextView birthTextview = binding.textviewBirthday;
        final ProgressBar loadingProgressBar = binding.loading;
    }
}
