package com.abasscodes.hockeyroster.contactdetail;

import android.support.v4.view.ViewPager;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.model.Contact;

/**
 * Created by abass on 3/18/17.
 */

class ContactDetailContract {

    public interface View extends BaseContract.View  {

        void showContact(Contact contact);
    }

    public interface Presenter extends BaseContract.Presenter<View> {

    }
}
