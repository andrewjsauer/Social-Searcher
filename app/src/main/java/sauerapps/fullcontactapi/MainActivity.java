package sauerapps.fullcontactapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    static final String API_KEY = "2c39190fed9dc94a";
    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";
        EditText phoneText;
        EditText emailText;
        ProgressBar progressBar;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbarMain);
            emailText = (EditText) findViewById(R.id.emailTextId);
            phoneText = (EditText) findViewById(R.id.phoneNumberId);
            progressBar = (ProgressBar) findViewById(R.id.progressBarId);

            if (getSupportActionBar() != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(R.string.app_name);
            }
        }


        public void goSearchEmail(View view) {

            if (emailText.getText().toString().trim().length() == 0) {
                Toast.makeText(MainActivity.this, "Please enter a email address, bruhhh", Toast.LENGTH_SHORT).show();
            } else {
                new RetrieveEmailTask().execute();
            }
        }

        public void goSearchPhone(View view) {

            if (phoneText.getText().toString().trim().length() == 0) {
                Toast.makeText(MainActivity.this, "Bruhhh, please enter a phone number ", Toast.LENGTH_SHORT).show();
            } else {
                new RetrievePhoneNumber().execute();
            }
        }

    @Override
    protected void onResume() {
        super.onResume();

        emailText.setText("");
        phoneText.setText("");

    }

    class RetrieveEmailTask extends AsyncTask<Void, Void, String> {

            String email;
        private Exception exception;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                email = emailText.getText().toString();
            }


            @Override
            protected String doInBackground(Void... params) {

                try {

                    URL url = new URL(API_URL + "email=" + email + "&style=dictionary&apiKey=" + API_KEY);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }


                        bufferedReader.close();
                        return stringBuilder.toString();


                    } finally {
                        urlConnection.disconnect();
                    }


                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }


            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }


            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                if (response == null) {
                    response = "Something went wrong";
                    Toast.makeText(MainActivity.this, "There was an error, try another email address", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                Log.i("INFO", response);

                Intent intent = new Intent(MainActivity.this, ShowResultsActivity.class);
                intent.putExtra(Constants.KEY, response.toString());
                startActivity(intent);

//                responseView.setText(response.toString());
            }
        }

    class RetrievePhoneNumber extends AsyncTask<Void, Void, String> {

        String phoneNumber;
        private Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            phoneNumber = phoneText.getText().toString();

            if (phoneNumber.equals("")) {
                Toast.makeText(MainActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL(API_URL + "phone=" + phoneNumber + "&style=dictionary&apiKey=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }


                    bufferedReader.close();
                    return stringBuilder.toString();


                } finally {
                    urlConnection.disconnect();
                }


            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response == null) {
                response = "OH, ERROR!";
                Toast.makeText(MainActivity.this, "There was an error, please try another phone number", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);

            Intent intent = new Intent(MainActivity.this, ShowResultsActivity.class);
            intent.putExtra(Constants.KEY, response.toString());
            startActivity(intent);

        }
    }
}