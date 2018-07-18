package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.*;
import java.util.stream.Collectors;

public class ContactHelper extends HelperBase {

    public ContactHelper (WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean create) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("middlename"), contactData.getMiddleName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("nickname"), contactData.getNickName());
        type(By.name("company"), contactData.getCompany());
        attach(By.name("photo"), contactData.getFile());

        if (create) {
            try {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }catch (NoSuchElementException e) {
                System.out.println("Обработано исключение, что нет указанной группы в модели");
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initContactModification(int numberOfContact) {
        click(By.xpath("//tr["+(numberOfContact+2)+"]/td[8]/a/img"));
    }
    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCash = null;
        returnToHomePage();
    }

    private void initContactModificationById(int id) {
        click(By.xpath("//input[@id='"+id+"']/../../td[8]/a"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContact(int numberOfContact) {
        wd.findElements(By.name("selected[]")).get(numberOfContact).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value=\"Delete\"]"));
    }
    public void delete(int index) {
        selectContact(index);
        deleteContact();
        acceptContactDeletion();
        returnToHomePage();
    }

    public void acceptContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public boolean thereAreContact() {
        if (isElementPresent(By.xpath("//tr[2]/td[8]/a/img"))) {
            return true;
        }else {
            return false;
        }
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        contactCash = null;
        returnToHomePage();
    }
    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public List<ContactData> list() {
        List <WebElement> elements = wd.findElements(By.name("entry"));
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (WebElement element: elements) {
            contacts.add(new ContactData()
                    .withId(Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("id")))
                    .withFirstName(element.findElement(By.xpath("td[3]")).getText())
                    .withLastName(element.findElement(By.xpath("td[2]")).getText())
                    .withAddress(element.findElement(By.xpath("td[4]")).getText())
                    .withEmailAddresses(element.findElement(By.xpath("td[5]")).getText())
                    .withAllPhones(element.findElement(By.xpath("td[6]")).getText()));
        }
        return contacts;

    }

    private Contacts contactCash = null;

    public Contacts all() {
        if (contactCash != null) {
            return new Contacts(contactCash);
        }
        List <WebElement> elements = wd.findElements(By.name("entry"));
        contactCash = new Contacts();
        for (WebElement element: elements) {
            contactCash.add(new ContactData()
                    .withId(Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("id")))
                    .withFirstName(element.findElement(By.xpath("td[3]")).getText())
                    .withLastName(element.findElement(By.xpath("td[2]")).getText())
                    .withAddress(element.findElement(By.xpath("td[4]")).getText())
                    .withEmailAddresses(element.findElement(By.xpath("td[5]")).getText())
                    .withAllPhones(element.findElement(By.xpath("td[6]")).getText()));
        }
        return new Contacts(contactCash);

    }

    public void delete(ContactData deleteContact) {
        selectContactById(deleteContact.getId());
        deleteContact();
        acceptContactDeletion();
        contactCash = null;
        returnToHomePage();
    }

    private void selectContactById(int id) {
        wd.findElement(By.xpath("//input[@id='"+id+"']")).click();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public String getAddressCP() {
        return wd.findElement(By.name("address")).getText();
    }

    public String getEmailAddressesCP() {
        return wd.findElement(By.name("email")).getAttribute("value")+"\n"+
                wd.findElement(By.name("email2")).getAttribute("value")+"\n"+
                wd.findElement(By.name("email3")).getAttribute("value");
    }

    public String getAllPhonesCP() {
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile =  wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        return Arrays.asList(home,mobile,work)
                .stream().filter(s->!s.equals("")).collect(Collectors.joining("\n"));

    }
}
