package com.abasscodes.hockeyroster.utils;

import com.abasscodes.hockeyroster.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abass on 3/18/17.
 */

public class TextFilterHelper {

    public static List<Contact> filter(List<Contact> contacts, String query) {
        query = query.toLowerCase();
        final List<Contact> filteredResults = new ArrayList<>();

        for (Contact contact : contacts) {
            final String contactName = contact.getName().toLowerCase();
            final String contactPosition =  contact.getPosition().toLowerCase();
            boolean nameMatch = contactName.startsWith(query) || contactName.contains(query);
            boolean positionMatch = contactPosition.startsWith(query) || contactPosition.contains(query);
            if (nameMatch || positionMatch) {
                filteredResults.add(contact);
            }
        }
        return filteredResults;
    }
}
