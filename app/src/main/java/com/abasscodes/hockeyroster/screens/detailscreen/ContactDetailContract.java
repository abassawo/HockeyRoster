package com.abasscodes.hockeyroster.screens.detailscreen;

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
