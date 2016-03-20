package com.example.stanley.assignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private static String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mText = (TextView) findViewById(R.id.txt);
        Button btn = (Button) findViewById(R.id.btn);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText.setText("");
                urlString = "http://api.randomuser.me/";
                new ProcessJSON().execute(urlString);
            }
        });
    }

    private class ProcessJSON extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream) {
            TextView mText = (TextView) findViewById(R.id.txt);
            /**
             * Important in JSON DATA
             -------------------------
             * Square bracket ([) represents a JSON array
             * Curly bracket ({) represents a JSON object
             * JSON object contains key/value pairs
             * Each key is a String and value may be different data types
             */
            //..........Process JSON DATA................
            if(stream !=null){
                try{
                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);
                    // Get the JSONArray result
                    JSONArray results = reader.getJSONArray("results");
                        //Get Values for each Objects
                        for(int i = 0; i < results.length();i++) {
                            JSONObject elem = results.getJSONObject(i);
                            if (elem != null) {
                                JSONObject prods = elem.getJSONObject("user");
                                if (prods != null) {
                                    String mail = prods.getString("email");
                                    String db = prods.getString("dob");
                                    String number = prods.getString("phone");
                                    for (int j = 0; j < prods.length(); j++) {
                                        JSONObject innerElem = prods.getJSONObject("name");
                                        if (innerElem != null) {
                                            String mtitle = innerElem.getString("title");
                                            String mfirst = innerElem.getString("first");
                                            String mlast = innerElem.getString("last");
                                            for (int k = 0; k < prods.length(); k++) {
                                                JSONObject innerElem1 = prods.getJSONObject("location");
                                                if (innerElem1 != null) {
                                                    String address = innerElem1.getString("street");
                                                    String code = innerElem1.getString("zip");

                                                            mText.setText("Getting Each Random User \n\n");
                                                            mText.setText(mText.getText() + "\t\tTitle :" + " " + mtitle + "\n");
                                                            mText.setText(mText.getText() + "\t\tName :" + " " + mfirst + " " + mlast + "\n");
                                                            mText.setText(mText.getText() + "\t\tNumber :" + " " + number + "\n");
                                                            mText.setText(mText.getText() + "\t\tD.O.B :" + " " + db + "\n");
                                                            mText.setText(mText.getText() + "\t\tZip Code :" + " " + code + "\n");
                                                            mText.setText(mText.getText() + "\t\tEmail :" + " " + mail + "\n");
                                                            mText.setText(mText.getText() + "\t\tLocation :" + " " + address + "\n");

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }


    }

}

