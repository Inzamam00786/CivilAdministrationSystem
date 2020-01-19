package com.inzamam.civiladministrationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UpdationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    Spinner spinner;
    TextView complabel;
    EditText loadedcompedittext;
    TextView updationoffnamelabel;
    TextView updation_offnametextview;
    TextView updation_statusLabel;
    Button updation_updatebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updation);
        toolbar=(Toolbar) findViewById(R.id.updationtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        complabel=(TextView) findViewById(R.id.complabel);
        loadedcompedittext=(EditText) findViewById(R.id.loadedcompeditText);
        updationoffnamelabel=(TextView) findViewById(R.id.updation_offnamelabel);
        updation_offnametextview=(TextView) findViewById(R.id.updation_offnametextview);
        updation_statusLabel=(TextView) findViewById(R.id.updation_updatestatuslabel);
        updation_updatebutton=(Button) findViewById(R.id.updation_updatebtn);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.myspinnerstatusvalues, android.R.layout.simple_spinner_dropdown_item);
        spinner=(Spinner) findViewById(R.id.statusspinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView=(TextView) view;
        Toast.makeText(this, textView.getText()+" ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onsubmitbtnClick(View v){
        complabel.setVisibility(View.VISIBLE);
        loadedcompedittext.setVisibility(View.VISIBLE);
        updationoffnamelabel.setVisibility(View.VISIBLE);
        updation_offnametextview.setVisibility(View.VISIBLE);
        updation_statusLabel.setVisibility(View.VISIBLE);
        updation_updatebutton.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }
}
