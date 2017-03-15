package com.abasscodes.hockeyroster.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.R;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseFragment<T extends BaseContract.Presenter> extends Fragment implements BaseContract.View {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected T presenter;

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roster, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onViewCreated();
    }

    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
    }

    @NonNull
    public abstract T createPresenter();

    protected abstract void handleAction(int actionText);

    @Override
    @CallSuper
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.v("Lifecycle onActivityResult: " + this);
        super.onActivityResult(requestCode, resultCode, data);
        presenter.bindView(this);
    }

    @Override
    @CallSuper
    public void onStart() {
        Timber.v("Lifecycle onStart: " + this);
        super.onStart();
        presenter.bindView(this);
    }

    @Override
    @CallSuper
    public void onResume() {
        Timber.v("Lifecycle onResume: " + this);
        super.onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        Timber.v("Lifecycle onPause: " + this);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        Timber.v("Lifecycle onStop: " + this);
        super.onStop();
        presenter.unbindView();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        Timber.v("Lifecycle onDestroyView: " + this);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        Timber.v("Lifecycle onDestroy: " + this);
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void onDetach() {
        Timber.v("Lifecycle onDetach: " + this + " from " + getContext());
        super.onDetach();
    }
}
