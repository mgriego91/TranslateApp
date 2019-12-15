package com.example.testagain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class is designed to gather a korean translation from Naver.com dictionary. It also provide three examples of the
 * translated word in context with sentences in both English and Korean.
 * @author Mark Griego, Minhoo Won, Dulu Park
 */
public class WebScrape {
    String word;//String object is created for word search

    /**
     * Creates WebScrape object
     * @param word is here for the user input text for translation needed/wanted
     */
    WebScrape(String word) {
        this.word = word;
    }

    /**
     * This is the accessor for gathering the translated text and contextual examples.
     * @return examples to provide printed examples
     */
    public String[] getExample() {
        // Array for saving translated words and examples
        String[] examples = new String[7];// Array for saving translated words and examples
        // Creates String to pass URL + the user input word into getUrlContents method.
        String html  = getUrlContents("https://endic.naver.com/search.nhn?sLn=en&searchOption=all&query=" + word);
        // All contents of the HTML code of the URL is parsed into a Document for "web-Scraping"
        Document doc = Jsoup.parse(html);
        // Saves the name of the first example into and array so that the list can be printed
        examples[0] = (doc.getElementsByClass("fnt_k05").get(0).text() + "\n");
        // This for loop gathers the example sentences in both Korean and English and
        // saves them into the array following word translation
        for(int i = 0, j = 0, k = 1, l = 2 ; i < 3; i++, j+=2, k += 2, l +=2 ) {
           examples[k] = (doc.getElementsByClass("fnt_e09 _ttsText").get(i).text() + "\n");
           examples[l] = (doc.getElementsByClass("N=a:xmp.detail").get(j).text() + "\n");
        }
        return examples;// Returns the array
    }

    /**
     * This method is used to connect to the url that is passed in from the user(via driver class)
     * @param theUrl Passed from the user and the String above called html
     * @return THe URL into a toString
     */
    private static String getUrlContents(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        // Wrapped all in one try/catch statement because many can throw an exception
        try {
            URL url = new URL(theUrl); //Creates a url object

            URLConnection connect = url.openConnection(); //Creates a url connection object

            // Wrap the urlconnection in a bufferedreader
            BufferedReader readHtml = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            String line; //String for HTML content
            // Read the URL from the urlconnection via the bufferedreader
            while ((line = readHtml.readLine()) != null) {
                content.append(line + "\n");
            }
            readHtml.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString(); // Returns into toString
    }
}
