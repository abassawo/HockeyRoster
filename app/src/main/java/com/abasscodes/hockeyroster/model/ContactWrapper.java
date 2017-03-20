package com.abasscodes.hockeyroster.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactWrapper {
    @SerializedName("roster")
    private List<Contact> contacts;

    public ContactWrapper(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
