package com.example.testagain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author
 */
public class WebScrape {
    String word;

    WebScrape(String word) {
        this.word = word;
    }

    public String[] getExample() {
        String[] examples = new String[7];


        String html  = getUrlContents("https://endic.naver.com/search.nhn?sLn=en&searchOption=all&query=" + word);
        Document doc = Jsoup.parse(html);

        examples[0] = (doc.getElementsByClass("fnt_k05").get(0).text() + "\n");

        for(int i = 0, j = 0, k = 1, l = 2 ; i < 3; i++, j+=2, k += 2, l +=2 ) {
           examples[k] = (doc.getElementsByClass("fnt_e09 _ttsText").get(i).text() + "\n");
           examples[l] = (doc.getElementsByClass("N=a:xmp.detail").get(j).text() + "\n");
        }
        return examples;
    }

    private static String getUrlContents(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        // wrapped all in one try/catch statement because many can throw an exception
        try {
            URL url = new URL(theUrl); //Creates a url object

            URLConnection connect = url.openConnection(); //Creates a url connection object

            // wrap the urlconnection in a bufferedreader
            BufferedReader readHtml = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            String line; //String for HTML content

            while ((line = readHtml.readLine()) != null) { //Read from the urlconnection via the bufferedreader
                content.append(line + "\n");
            }
            readHtml.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}