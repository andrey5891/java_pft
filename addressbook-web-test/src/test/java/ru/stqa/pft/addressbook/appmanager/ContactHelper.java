package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContact(int numberOfContact) {
        wd.findElements(By.name("selected[]")).get(numberOfContact).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value=\"Delete\"]"));
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

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        returnToHomePage();
    }
    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public List<ContactData> getContactCount() {
        List <WebElement> elements = wd.findElements(By.name("entry"));
        List <ContactData> contacts = new ArrayList<>();
        for (WebElement element: elements) {
            contacts.add(new ContactData(
                    Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("id")),
                    element.findElement(By.xpath("td[3]")).getText(),
                    element.findElement(By.xpath("td[2]")).getText()));
        }
        return contacts;

    }
}
