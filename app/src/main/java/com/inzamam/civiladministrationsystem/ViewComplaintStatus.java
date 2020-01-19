package com.inzamam.civiladministrationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewComplaintStatus extends AppCompatActivity {
    Toolbar toolbar;
    TextView labelcomptextview, compsatuslabeltextview, statustextview, offnamelabelgtextview, offnamefromdbtv;
    EditText complaintedittext, compIDEdittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomplaintstatus);
        toolbar=(Toolbar) findViewById(R.id.viewstatustoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        labelcomptextview=(TextView) findViewById(R.id.labelcomptextView);
        compsatuslabeltextview=(TextView) findViewById(R.id.compstatuslabelTextView);
        statustextview=(TextView) findViewById(R.id.statusTextView);
        offnamelabelgtextview=(TextView) findViewById(R.id.offnamelabelTextView);
        offnamefromdbtv=(TextView) findViewById(R.id.officernamefromdbtv);
        complaintedittext=(EditText) findViewById(R.id.complaintEditText);
        compIDEdittext=(EditText) findViewById(R.id.compideditText);

    }
    public void onSubmitBtnClick(View view){
        labelcomptextview.setVisibility(View.VISIBLE);
        compsatuslabeltextview.setVisibility(View.VISIBLE);
        statustextview.setVisibility(View.VISIBLE);
        offnamelabelgtextview.setVisibility(View.VISIBLE);
        offnamefromdbtv.setVisibility(View.VISIBLE);
        complaintedittext.setVisibility(View.VISIBLE);
    }
}
