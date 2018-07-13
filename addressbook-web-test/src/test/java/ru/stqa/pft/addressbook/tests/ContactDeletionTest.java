package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactDeletionTest extends TestBase{

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();
        if (! app.getContactHelper().thereAreContact()) {
            app.getContactHelper().createContact(new ContactData("Иван", "Иванович", "Иванов",
                    "Ivan", "Рога и копыта", "test1"));
        }
        List <ContactData> before = app.getContactHelper().getContactCount();
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteContact();
        app.getContactHelper().acceptContactDeletion();
        app.getNavigationHelper().gotoHomePage();
        List <ContactData> after = app.getContactHelper().getContactCount();
        before.remove(before.size()-1);
        Assert.assertEquals(before, after);
    }
}
