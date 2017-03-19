package com.abasscodes.hockeyroster.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContactWrapper {
    @SerializedName("roster")
    private List<Contact> contacts;

    public ContactWrapper(){
        this.contacts = new ArrayList<>();
    }

    public ContactWrapper(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Contact contact : contacts) {
            stringBuilder.append(contact.getName() +
                                         contact.getPosition() +
                                         contact.getImageUrl() + "/n");
        }
        return stringBuilder.toString();
    }
}
