package sk.tomas.utils;

import lombok.extern.slf4j.Slf4j;
import sk.tomas.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileUtils {
    private List<Item> items;
    private final char DEFAULT_SEPARATOR = ';';
    private String category;

    public FileUtils(List<Item> items, String category) {
        this.items = items;
        this.category = category;
    }

    public void writeToCsv() {
        log.info("Start writing to csv");
        try (FileWriter csvWriter = new FileWriter(category + ".csv")) {
            writeCsvLine(csvWriter, Arrays.asList("kod","name","category","price without wat"));
            for (Item item : items) {
                List<String> values = Arrays.asList(item.getId(), item.getName(), item.getCategory(), item.getPriceWithoutWat());
                writeCsvLine(csvWriter, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public void writeToJson() {
        log.info("Start writing to json");
        try (FileWriter fileWriter = new FileWriter(category + ".json")) {
            String newLine = System.getProperty("line.separator");
            for (Item item : items) {
                fileWriter.write(item + newLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


    private void writeCsvLine(Writer w, List<String> items) throws IOException {
        writeCsvLine(w, items, DEFAULT_SEPARATOR, ' ');
    }


    private void writeCsvLine(Writer w, List<String> items, char separators) throws IOException {
        writeCsvLine(w, items, separators, ' ');
    }


    private String followCVSformat(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }


    private void writeCsvLine(Writer w, List<String> items, char separators, char customQuote) throws IOException {
        boolean first = true;
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }
        StringBuilder sb = new StringBuilder();
        for (String value : items) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }
            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }


}