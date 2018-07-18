package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class contactInformationTest extends TestBase {
    @Test
    public void testContactInformation() {
        app.goTo().homePage();
        Contacts contacts = app.contact().all();
        ContactData contact = contacts.stream().iterator().next();
        //System.out.println(contact.getAddress()+" "+contact.getEmailAddresses()+" " + contact.getAllPhones());
        app.contact().click(By.xpath(String.format("//input[@id='%s']/../../td[8]/a", contact.getId())));
        String addressCP = app.contact().getAddressCP();
        String emailAddressesCP = app.contact().getEmailAddressesCP();
        String allPhonesCP = app.contact().getAllPhonesCP()
                .replaceAll(" ", "").replaceAll("[-()]","");
        assertThat(contact.getAddress(), equalTo(addressCP));
        assertThat(contact.getEmailAddresses(), equalTo(emailAddressesCP));
        assertThat(contact.getAllPhones(), equalTo(allPhonesCP));
    }
}
