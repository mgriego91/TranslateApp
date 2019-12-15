package com.example.testagain;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URLEncoder;

/**
 * Class is designed to gather and parse from the URL, utilizing a personalized Google Cloud API Key
 * using Android LoopJ to allow for a JSON parse of the information gathered from the URL
 */
public class Http {
    private static final String BASE_URL = "https://translation.googleapis.com/language/translate/v2?";// URL without Key
    private static final String KEY = "Your Key Here"; // Key to add at the end of the URL

    // This client is created for gathering the translated text, source and destination languages
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * This creates Strings of all the information gathered from the URL
     * @param transText is from the user input text/translated text
     * @param sourceLang The UTF-8 code for the source language
     * @param destLang The UTF-8 code for the Destination language
     * @param responseHandler A get request for the URL
     */
    public static void post(String transText, String sourceLang, String destLang, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(transText, sourceLang, destLang), responseHandler);
    }

    /**
     * This is a String to add the API Key to the URL String
     * @param key Passed in from personalized API Key
     * @return HTML String section
     */
    private static String makeKeyChunk(String key) {
        return "key=" + KEY;
    }

    /**
     * This String adds the encoded text(UTF-8) into the URL to use proper language encoding for the
     * source language and the destination language
     * @param transText User input text
     * @return the encoded text as a URL section
     */
    private static String makeTransChunk(String transText) {
        String encodedText = URLEncoder.encode(transText);
        return "&amp;q=" + encodedText;
    }

    /**
     * This String is placed into the URl with the source language UTF-8 character code
     * @param langSource Source language UTF-8 code
     * @return URL section for source language
     */
    private static String langSource(String langSource) {
        return "&amp;source=" + langSource;
    }

    /**
     * This String is placed into the URl with the destination language UTF-8 character code
     * @param langDest Is for the destination language UTF-8 code
     * @return Returns this completed section of the URL
     */
    private static String langDest(String langDest) {
        return "&amp;target=" + langDest;

    }

    /**
     * This method gathers all the information generating a complete URL to connect to and get translations from the
     * @param transText is from the user text input
     * @param sourceLang is from the source language UTF-8 code
     * @param destLang is from the destination language UTF-8 code
     * @return Returns all the combined gathered strings as a completed String for the URL
     */
    private static String getAbsoluteUrl(String transText, String sourceLang, String destLang) {
        String apiUrl = BASE_URL + makeKeyChunk(KEY) + makeTransChunk(transText) + langSource(sourceLang) + langDest(destLang);
        return apiUrl;
    }

}
