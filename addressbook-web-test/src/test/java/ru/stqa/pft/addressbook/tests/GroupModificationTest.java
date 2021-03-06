package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().groupPage();
        if (app.group().all().size() == 0){
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
        }
    }
    @Test
    public void testGroupModification() {
        Groups before = app.group().all();

        GroupData modifyGroup = before.iterator().next();
        GroupData group =
                new GroupData().withId(modifyGroup.getId()).withName("test1_m").withHeader("test2_m").withFooter("test3_m");
        app.group().modify(group);
        assertThat (app.group().getGroupCount(), equalTo(before.size()));
        Groups after = app.group().all();
        before.remove(modifyGroup);
        before.add(group);
        assertThat(after, equalTo(before.without(modifyGroup).withAdded(group)));
    }


}
