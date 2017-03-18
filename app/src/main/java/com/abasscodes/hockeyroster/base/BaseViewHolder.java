package com.abasscodes.hockeyroster.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.R;

import butterknife.ButterKnife;

/**
 * Created by abass on 3/17/17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected OnItemClickListener listener;

    public BaseViewHolder(ViewGroup parent, @LayoutRes int layoutRes, OnItemClickListener listener) {
        super(inflateView(parent, layoutRes));
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    private static View inflateView(ViewGroup parent, @LayoutRes int layoutRes) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(layoutRes, parent, false);
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
