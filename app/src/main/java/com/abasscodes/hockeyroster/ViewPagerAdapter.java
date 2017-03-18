package com.abasscodes.hockeyroster;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.base.BaseFragment;
import com.abasscodes.hockeyroster.contactdetail.ContactDetailFragment;
import com.abasscodes.hockeyroster.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abass on 3/17/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());
    }

    public void setData(List<Contact> contacts){
        for(Contact contact : contacts){
            addFragmentForContact(contact);
        }
        notifyDataSetChanged();
    }

    private void addFragmentForContact(Contact contact) {
        fragments.add(ContactDetailFragment.newInstance(contact));
        fragmentTitles.add(contact.getName());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
        fragments.set(position, fragment);
        return fragment;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
