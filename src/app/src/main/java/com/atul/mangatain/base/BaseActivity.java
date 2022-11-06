package com.atul.mangatain.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<VB extends ViewBinding, VM extends ViewModel> extends AppCompatActivity implements View.OnClickListener {

    protected VB binding = null;

    protected VM viewModel = null;

    protected abstract VB provideBinding(LayoutInflater inflater);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = provideBinding(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        createView();
        setupViewClickListener();
        registerObserver();
    }

    protected void registerObserver() {

    }

    private Class<VM> getViewModelClass() {
        Class<VM> clazz = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
        return clazz;
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
