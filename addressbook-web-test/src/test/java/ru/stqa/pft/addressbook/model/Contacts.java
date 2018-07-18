package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> {
    private Set<ContactData> delegate;

    public Contacts(Set<ContactData> delegate) {
        this.delegate = new HashSet<ContactData>(delegate);
    }

    public Contacts() {
        this.delegate = new HashSet<ContactData>();
    }

    @Override
    protected Set<ContactData> delegate() {
        return this.delegate;
    }


    public Contacts withAdded (ContactData contact) {
        Contacts contacts = new Contacts(this.delegate);
        contacts.add(contact);
        return contacts;
    }

    public Contacts without (ContactData contact) {
        Contacts contacts = new Contacts(this.delegate);
        contacts.remove(contact);
        return contacts;
    }


}
