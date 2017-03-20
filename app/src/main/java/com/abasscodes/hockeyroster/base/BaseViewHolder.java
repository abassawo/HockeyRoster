package com.abasscodes.hockeyroster.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.model.Contact;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected final OnItemClickListener listener;

    protected BaseViewHolder(ViewGroup parent,
                             @LayoutRes int layoutRes,
                             OnItemClickListener listener) {
        super(inflateView(parent, layoutRes));
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    private static View inflateView(ViewGroup parent, @LayoutRes int layoutRes) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(layoutRes, parent, false);
    }

    public interface OnItemClickListener {
        void onContactClicked(Contact contact);
    }
}
