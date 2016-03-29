package sauerapps.fullcontactapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ShowResultsActivity extends AppCompatActivity {

    String response;
    TextView responseView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Results");

            }
        }

        responseView = (TextView) findViewById(R.id.responseViewId);

        Intent intent = this.getIntent();
        response = intent.getStringExtra(Constants.KEY);

        responseView.setText(response);

    }
}
