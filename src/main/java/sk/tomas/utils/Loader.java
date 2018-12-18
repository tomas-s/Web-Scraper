package sk.tomas.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class Loader {

    private String category;

    public ArrayList<String> loadCategories() {
        final ArrayList<String> categorysToRead = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(category + ".txt").getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (!"".equals(line)) {
                    categorysToRead.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return categorysToRead;
    }


}
