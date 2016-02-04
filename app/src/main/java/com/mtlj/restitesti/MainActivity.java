package com.mtlj.restitesti;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String url = "http://jsonplaceholder.typicode.com/posts/1";
    String results = "";
    TextView messageTextView;
    public final static String EXTRA_MESSAGE = "com.mtlj.restitesti.MESSAGE";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10);
        progressBar.setVisibility(View.GONE);
    }



    public void query(View v)   {

        progressBar.setVisibility(View.VISIBLE);

        progressBar.setProgress(2);
//        RestPull restPull = new RestPull();
        new RestPull().execute(url);
//        messageTextView.setText(results);


    }


    private class RestPull extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            publishProgress(2);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String urlString = params[0];
            System.out.println("URLI: " +urlString);
            String resultToDisplay = "";
            InputStream in = null;

            try {
                publishProgress(4);

                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());



                publishProgress(8);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                byte[] contents = new byte[1024];
                int bytesRead=0;

                while( (bytesRead = in.read(contents)) != -1){
                    resultToDisplay = new String(contents, 0, bytesRead);
                 }
            }
            catch (Exception e)   {

                System.out.println("ERROR: " +e);

            }
//            parse
            XmlPullParserFactory xmlPullParserFactory;

//            try {
//                xmlPullParserFactory = XmlPullParserFactory.newInstance();
//                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
//
//                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//                xmlPullParser.setInput(in, null);
//
//
//
//            }
//            catch (Exception e)   {
//                System.out.println("ERROR: " +e);
//
//            }

            publishProgress(10);
            System.out.println("RESULTS: " +resultToDisplay);
            return resultToDisplay;
        }


        @Override
        protected void onPostExecute(String result)  {

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... values)   {

            progressBar.setProgress(values[0]);
        }
    }
}
