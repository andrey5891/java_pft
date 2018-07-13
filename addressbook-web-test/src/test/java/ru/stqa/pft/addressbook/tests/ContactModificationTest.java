package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().thereAreContact()) {
            app.getContactHelper().createContact(new ContactData("Иван", "Иванович", "Иванов",
                    "Ivan", "Рога и копыта", "test1"));
        }

        List<ContactData> before = app.getContactHelper().getContactCount();


        app.getContactHelper().initContactModification(0);
        ContactData contact = new ContactData(before.get(0).getId(),"Петр", "Петров");
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();

        List <ContactData> after = app.getContactHelper().getContactCount();
        before.remove(0);
        before.add(contact);

        Comparator<? super ContactData> comparatorById = (o1, o2)-> Integer.compare(o1.getId(), o2.getId());
        after.sort(comparatorById);
        before.sort(comparatorById);
        Assert.assertEquals(before, after);
    }
}
