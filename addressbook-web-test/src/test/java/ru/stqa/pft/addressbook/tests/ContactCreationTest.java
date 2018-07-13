package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase{

    @Test
    public void ContactCreationTest() {
        app.getNavigationHelper().gotoHomePage();

        List<ContactData> before = app.getContactHelper().getContactCount();
        ContactData contact = new ContactData("Иван", "Иванович", "Иванов",
                "Ivan", "Рога и копыта", "test1");
        app.getContactHelper().createContact(contact);

        List <ContactData> after = app.getContactHelper().getContactCount();
        before.add(contact);

        Comparator<? super ContactData> comparatorById = (o1, o2)-> Integer.compare(o1.getId(), o2.getId());
        after.sort(comparatorById);
        before.sort(comparatorById);

        Assert.assertEquals(before, after);
    }
}
