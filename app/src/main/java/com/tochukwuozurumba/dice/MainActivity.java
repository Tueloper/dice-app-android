package com.tochukwuozurumba.dice;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    declare global variables
    private Spinner spinner;
//    private List<String> spinnerList;
    private String another;
    private Button customDieButton;
    private Button rollButtonOnce;
    private Button rollButtonTwice;
    private TextView rollOnceDisplay;
    private TextView rollTwiceDisplay;
    private EditText customDieInput;
    private ArrayAdapter<Integer> spinnerAdapter;
    private Integer getCustomDieValue;
    private ArrayList<Integer> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        define variables
        spinner = findViewById(R.id.spinner);
        customDieInput = findViewById(R.id.custom_die);
        customDieButton = findViewById(R.id.custom_die_button);
        rollButtonOnce = findViewById(R.id.roll_once_button);
        rollButtonTwice = findViewById(R.id.roll_twice_button);

//        set button view instance
        customDieButton.setOnClickListener(this);
        rollButtonOnce.setOnClickListener(this);
        rollButtonTwice.setOnClickListener(this);

//        get spinner
        spinnerList = new ArrayList<Integer>();
        spinnerList.add(2);
        spinnerList.add(4);
        spinnerList.add(6);
        spinnerList.add(8);
        spinnerAdapter = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerAdapter);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.custom_die_button:
                addCustomDie();
        }
    }

    private void addCustomDie() {
//        get custom die value
        getCustomDieValue = Integer.parseInt(customDieInput.getText().toString());

//        add to custom die to list
        spinnerList.add(getCustomDieValue);

        Log.d("SpinnerList: ", spinnerList.toString());

//        notify adapter that the list has been updated
        spinnerAdapter.notifyDataSetChanged();

//        reset custom input
        customDieInput.setText("");
    }
}