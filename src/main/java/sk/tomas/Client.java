package sk.tomas;

import com.gargoylesoftware.htmlunit.WebClient;
public class Client {
    public static WebClient initClient(){
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        return client;
    }
}
