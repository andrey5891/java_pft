package ru.stqa.pft.addressbook.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
    @Parameter (names = "-c", description = "Count groups")
    int count;
    @Parameter (names = "-f", description = "Target file")
    String targetFile;
    @Parameter (names = "-d", description = "Data format")
    String format;

    public static void main (String [] args) throws IOException {
        GroupDataGenerator groupDataGenerator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(groupDataGenerator);
        try {
            jCommander.parse(args);
        }catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        groupDataGenerator.run();
    }

    public void run() throws IOException {
        List<GroupData> groups = groups(count);
        if (format.equals("csv")) {
            saveDataCVS(groups, new File(targetFile));
        }else if (format.equals("xml")) {
            saveDateXml(groups, new File(targetFile));
        }else if (format.equals("json")) {
            saveDataJson(groups, new File(targetFile));
        }else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveDataJson(List<GroupData> groups, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
            //writer.close();
        }
    }

    private void saveDateXml(List<GroupData> groups, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
            //writer.close();
        }
    }


    public List<GroupData> groups(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            groups.add(new GroupData().withName(String.format("JSONtest %s", i)).withHeader(String.format("header %s", i))
                    .withFooter(String.format("footer %s", i)));
        }
        return groups;
    }
    public void saveDataCVS (List<GroupData> groups, File file) throws IOException {
       try (Writer writer = new FileWriter(file)) {
           for (GroupData g : groups) {
               writer.write(String.format("%s;%s;%s\n", g.getName(), g.getHeader(), g.getFooter()));
           }
           //writer.close();
       }
    }
}
