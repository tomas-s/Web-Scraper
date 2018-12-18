package sk.tomas;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class SubCategory {
    private final String categoryUrl;
    private String urlToRead;
    private final WebClient client;
    private Set<Item> pruductList = new HashSet<>();
    private final String LIST_ITEM_XPATH = "//li[@class=\"list-products__item\"]";
    private final String ID_XPATH = ".//div[@class=\"list-products__item__top__code\"]";
    private final String NAME_XPATH = ".//h3[@class=\"list-products__item__top__title\"]";
    private final String PRICE_WITH_WAT_XPATH = ".//span[@class=\"list-products__item__bottom__info-box__prices__with-wat\"]";
    private final String PRICE_WITHOUT_WAT_XPATH = ".//strong[@class=\"list-products__item__bottom__info-box__prices__without-wat\"]";
    private final String CATEGORY_XPATH = "//span[@class=\"box-breadcrumb__item__last\"]";

    public SubCategory(String categoryUrl, WebClient client) {
        this.categoryUrl = categoryUrl;
        this.urlToRead = categoryUrl;
        this.client = client;
    }

    public Set<Item> getPruductList() {
        return pruductList;
    }


    public void readSubCategory() {
        HtmlPage htmlPage;
        do {
            htmlPage = readUrl(urlToRead);
            readThirtyItems(htmlPage);
        }
        while (isAnotherPageToRead(htmlPage));
    }

    private HtmlPage readUrl(String url) {
        HtmlPage page = null;
        try {
            String searchUrl = url + URLEncoder.encode("", "UTF-8");
            page = client.getPage(searchUrl);
        } catch (IOException e) {
            log.error("error");
            e.printStackTrace();
        }
        return page;
    }

    private Set<Item> readThirtyItems(HtmlPage page) {
        HtmlElement categoryElement = page.getFirstByXPath(CATEGORY_XPATH);
        String category = categoryElement.getFirstChild().asText();
        List<HtmlElement> itemsList = (List<HtmlElement>) page.getByXPath(LIST_ITEM_XPATH);
        for (HtmlElement item : itemsList) {
            HtmlElement id = item.getFirstByXPath(ID_XPATH);
            HtmlElement name = item.getFirstByXPath(NAME_XPATH);
            HtmlElement priceWithWat = item.getFirstByXPath(PRICE_WITH_WAT_XPATH);
            HtmlElement priceWithoutWat = item.getFirstByXPath(PRICE_WITHOUT_WAT_XPATH);
            Item itemToAdd = new Item(id != null ? id.getFirstChild().asText() : "", name != null ? name.getFirstChild().asText() : "", priceWithWat != null ? priceWithWat.getFirstChild().asText() : "", priceWithoutWat != null ? priceWithoutWat.getFirstChild().asText() : "", category != null ? category : "");
            pruductList.add(itemToAdd);
        }
        return pruductList;
    }

    private boolean isAnotherPageToRead(HtmlPage page) {
        HtmlElement nextPage = page.getFirstByXPath("//div[@class=\"next-products js-NextProductWrap\"]");
        if (nextPage == null) {
            return false;
        } else {
            urlToRead = nextPage.getFirstChild().getAttributes().getNamedItem("href").getNodeValue();
            return true;
        }
    }
}
