package com.tutoring.apps;

import com.tutoring.libs.list.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 8/20/12
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class WikipediaCrawl {
    public static void main(String[] args) {
        String start_article = "http://en.wikipedia.org/wiki/Contempt_of_court";
        Collection<String> links = getLinks(start_article);
        for (String link : links) {
            System.out.println(link);
        }
    }

    public static Collection<String> getLinks(String article) {
        Document doc = null;
        try {
            doc = Jsoup.connect(article).get();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Element body = doc.getElementById("mw-content-text");
        Elements anchors = body.select("a[href]");
        ArrayList<String> links = new ArrayList<String>();
        for (Element anchor : anchors) {
            String link = anchor.attr("href");
            if (link.startsWith("/wiki/") && !link.contains(":")) {
                links.add(anchor.attr("abs:href"));
            }
        }
        return links;
    }
}
