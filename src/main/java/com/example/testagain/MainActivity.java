package com.example.testagain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/**
 * This is the Main driver for the WebsScrape and Http classes
 */
public class MainActivity extends AppCompatActivity {
    private TextView translationTextView;// Creates object to allow for view of text after translation
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Runs code after translation and buttons are clicked in App
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Utilizes code from activity_main.xml file to show view
        Button button = findViewById(R.id.translateButton); // Initializes button to run code
        final EditText input = findViewById(R.id.textInput); // For user input text
        translationTextView = findViewById(R.id.translation); // Puts value in the TextView
        button.setOnClickListener(new View.OnClickListener() { // Runs to "listen" for the button click
            /**
             * This method is Android standard to run code as soon as a button in the app is clicked
             * @param v is passed in
             */
            @Override
            public void onClick(View v) {
                final String translationString = input.getText().toString(); // Places user text(word to translate) into string
                // Uses Http class to gather information from URL and then parses
                Http.post(translationString, "en", "ko", new JsonHttpResponseHandler(){
                    /**
                     * This is for successful connection to internet to ensure the URL can be reached
                     * which is imperative for successful app usage.
                     * @param statusCode Returns code depending on connection
                     * @param headers Array for collecting headers
                     * @param response Returns response of connection
                     */
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONObject serverResp = new JSONObject(response.toString());
                            JSONObject jsonObject = serverResp.getJSONObject("data");
                            JSONArray transObject = jsonObject.getJSONArray("translations");
                            JSONObject transObject2 =  transObject.getJSONObject(0);
                            translationTextView.setText(transObject2.getString("translatedText"));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
