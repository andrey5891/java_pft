package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroup(int numberOfGroup) {
        wd.findElements(By.name("selected[]")).get(numberOfGroup).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public boolean thereAreGroup() {
        if (isElementPresent(By.name("selected[]"))) {
            return true;
        }
        else {
            return false;
        }
    }

    public void createGroup(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        returnToGroupPage();

    }
    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<GroupData> getGroupList() {
       List<WebElement> elements = wd.findElements(By.xpath("//span[@class=\"group\"]"));
       List<GroupData> groups = new ArrayList<>();
       for (WebElement element: elements) {
           groups.add(new GroupData(Integer.parseInt
                   (element.findElement(By.tagName("input")).getAttribute("value")),element.getText(), null, null));
       }
       return groups;
    }
}
