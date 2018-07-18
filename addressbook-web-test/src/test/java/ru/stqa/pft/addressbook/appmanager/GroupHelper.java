package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void delete(int index) {
        selectGroup(index);
        deleteSelectedGroups();
        returnToGroupPage();
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

    public void modify(int index, GroupData group) {
        selectGroup(index);
        initGroupModification();
        fillGroupForm(group);
        submitGroupModification();
        returnToGroupPage();
    }

    public boolean thereAreGroup() {
        if (isElementPresent(By.name("selected[]"))) {
            return true;
        }
        else {
            return false;
        }
    }

    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        groupCash = null;
        returnToGroupPage();

    }
    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<GroupData> list() {
       List<WebElement> elements = wd.findElements(By.xpath("//span[@class=\"group\"]"));
       List<GroupData> groups = new ArrayList<>();
       for (WebElement element: elements) {
           String name = element.getText();
           int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
           groups.add(new GroupData().withId(id).withName(name));
       }
       return groups;
    }


    private Groups groupCash = null;

    public Groups all() {
        if (groupCash != null) {
            return new Groups(groupCash);
        }

        List<WebElement> elements = wd.findElements(By.xpath("//span[@class=\"group\"]"));
        groupCash = new Groups();
        for (WebElement element: elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groupCash.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupCash);
    }

    public void delete(GroupData deletedGroup) {
        selectGroupById(deletedGroup.getId());
        deleteSelectedGroups();
        groupCash = null;
        returnToGroupPage();
    }

    private void selectGroupById(int id) {
        wd.findElement(By.xpath("//input[@value='"+id+"']")).click();
    }

    public void modify(GroupData groupForModify) {
        selectGroupById(groupForModify.getId());
        initGroupModification();
        fillGroupForm(groupForModify);
        submitGroupModification();
        groupCash = null;
        returnToGroupPage();
    }
}
