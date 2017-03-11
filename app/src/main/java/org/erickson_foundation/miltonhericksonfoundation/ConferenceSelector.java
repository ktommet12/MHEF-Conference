package org.erickson_foundation.miltonhericksonfoundation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;

public class ConferenceSelector extends AppCompatActivity implements View.OnClickListener {
    TableRow couples, evolution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_selector);

        couples = (TableRow) findViewById(R.id.couples_conferece);
        evolution = (TableRow) findViewById(R.id.evolution_conferece);

        couples.setOnClickListener(this);
        evolution.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()){
            case R.id.couples_conferece:
                intent.putExtra("ConferenceName", "Couples Conference");
                break;
            case R.id.evolution_conferece:
                intent.putExtra("ConferenceName", "Evolution of Psychotherapy");
                break;
        }
        startActivity(intent);
    }
}
