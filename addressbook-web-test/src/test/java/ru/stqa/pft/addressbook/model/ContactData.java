package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String nickName;
    private final String company;
    private final String group;
    private int id;



    public ContactData(String firstName, String middleName, String lastName, String nickName, String company, String group) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.company = company;
        this.group = group;
    }
    public ContactData(String firstName, String lastName) {
        this.id = 0;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
        this.nickName = null;
        this.company = null;
        this.group = null;
    }
    public ContactData(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
        this.nickName = null;
        this.company = null;
        this.group = null;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCompany() {
        return company;
    }

    public String getGroup() {
        return group;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public int getId() {
        return this.id;
    }
}