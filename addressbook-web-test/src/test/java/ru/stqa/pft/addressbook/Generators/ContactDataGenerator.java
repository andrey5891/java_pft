package ru.stqa.pft.addressbook.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter (names = "-c", description = "Count contact")
    int count;
    @Parameter (names = "-f", description = "Target file")
    String file;

    public static void main (String [] args) throws IOException {
        ContactDataGenerator contactDataGenerator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(contactDataGenerator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex){
            jCommander.usage();
        }
        contactDataGenerator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = contacts(count);
        saveJson(contacts);
    }

    private void saveJson(List<ContactData> contacts) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(new File(file))) {
            writer.write(json);
            //writer.close();
        }
    }

    private List<ContactData> contacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            ContactData contact = new ContactData()
                    .withFirstName(String.format("J Имя %s", i)).withLastName(String.format("J Фамилия %s", i));
            contacts.add(contact);
        }
        return contacts;
    }
}
