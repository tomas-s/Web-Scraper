package sk.tomas;

import com.gargoylesoftware.htmlunit.WebClient;
import sk.tomas.utils.FileUtils;
import sk.tomas.utils.Loader;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        final String CATEGORY_TO_LOAD = "kancelarsky_nabytok";

        final ArrayList<Item> finalProducList = new ArrayList<>();
        Loader loader = new Loader(CATEGORY_TO_LOAD);
        final ArrayList<String> categoriesToLoad = loader.loadCategories();
        WebClient webClient = Client.initClient();
        categoriesToLoad.forEach(category -> {
            SubCategory subCategory = new SubCategory(category, webClient);
            subCategory.readSubCategory();
            finalProducList.addAll(subCategory.getPruductList());
        });
        FileUtils fileUtils = new FileUtils(finalProducList, CATEGORY_TO_LOAD);
        fileUtils.writeToJson();
        fileUtils.writeToCsv();
    }
}

