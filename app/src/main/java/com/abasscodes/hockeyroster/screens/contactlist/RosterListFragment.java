package com.abasscodes.hockeyroster.screens.contactlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseFragment;
import com.abasscodes.hockeyroster.model.Contact;

import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

public class RosterListFragment extends BaseFragment<RosterListContract.Presenter> implements RosterListContract.View {
    private ContactAdapter contactAdapter = new ContactAdapter();
    private Snackbar snackbar;
    @BindView(R.id.roster_recycler_view)
    RecyclerView rosterRecyclerView;

    @Override
    public void dismissSnackBar() {
        if(snackbar != null) {
            snackbar.dismiss();
        }
    }

    @NonNull
    @Override
    public RosterListContract.Presenter createPresenter() {
        return new RosterListPresenter(this);
    }

    @Override
    protected void handleAction(int actionText) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        rosterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rosterRecyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void showSnackbar(@StringRes int messageRes, @StringRes final int actionText) {
        View view = getActivity().findViewById(R.id.coord_layout);
        snackbar = Snackbar.make(view, messageRes, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText, v -> handleAction(actionText));
        snackbar.show();
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        Timber.d("Contacts size " + contacts.size());
        contactAdapter.setData(contacts);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
    }
}
