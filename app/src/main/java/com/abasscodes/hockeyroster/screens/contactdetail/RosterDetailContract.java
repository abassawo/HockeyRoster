package com.abasscodes.hockeyroster.screens.contactdetail;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.model.Contact;

public class RosterDetailContract {
    interface View extends BaseContract.View {
        void showContact(Contact contact);
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
