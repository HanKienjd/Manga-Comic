package com.atul.mangatain.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment implements View.OnClickListener {
    protected VB binding = null;

    protected VM viewModel = null;

    protected abstract VB provideBinding(LayoutInflater inflater, ViewGroup container, Boolean attachToParent);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
    }

    private Class<VM> getViewModelClass() {
        Class<VM> clazz = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
        return clazz;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = provideBinding(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createView();
        setupViewClickListener();
        registerObserver();
    }

    protected void registerObserver() {

    }


    protected abstract void createView();

    protected void viewCreated() {

    }

    protected void setupViewClickListener() {

    }

    @Override
    public void onClick(View v) {
        onViewClicked(v);
    }

    protected void onViewClicked(View view) {

    }
}
