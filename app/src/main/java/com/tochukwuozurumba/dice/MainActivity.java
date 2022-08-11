package com.tochukwuozurumba.dice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    declare global variables
    private Spinner spinner;
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
    private SharedPreferences prefs;
    private int selectedSpinnerOption;
    private Die dice;
    private ArrayAdapter<String> historyAdapter;
    private ArrayList<String> diceHistoryListArray;
    private ListView diceHistoryDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        shared preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

//        define variables
        spinner = findViewById(R.id.spinner);
        customDieInput = findViewById(R.id.custom_die);
        customDieButton = findViewById(R.id.custom_die_button);
        rollButtonOnce = findViewById(R.id.roll_once_button);
        rollButtonTwice = findViewById(R.id.roll_twice_button);
        rollOnceDisplay = findViewById(R.id.roll_once_score);
        rollTwiceDisplay = findViewById(R.id.roll_twice_score);

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerOption = spinnerList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //        instanstiate Die class
        Log.d("select", String.valueOf(selectedSpinnerOption));
        dice = new Die(selectedSpinnerOption);

//        history
        diceHistoryListArray = new ArrayList<>();
        historyAdapter = new ArrayAdapter<String>(this, R.layout.recycler_view, diceHistoryListArray);
        diceHistoryDisplay = findViewById(R.id.view_history);
        diceHistoryDisplay.setAdapter(historyAdapter);
    }

    protected void onResume(){
        super.onResume();

//        Log.d("interger", String.valueOf(prefs.getInt("iniii", Integer.parseInt(""))));
//        String stringSpinnerList = prefs.getString("prefs_spinner_list", "");
//        Log.d("onResume", stringSpinnerList);


        spinnerAdapter = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerAdapter);
    }

//    protected void onPause() {
//        saveData();
//        Log.d("Pause", "App is paused");
//        super.onPause();
//    }
//
//    protected void onStop() {
//        saveData();
//        super.onStop();
//    }
//    protected void onDestroy() {
//        saveData();
//        super.onDestroy();
//    }

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
                break;
            case R.id.roll_once_button:
                rollOnce();
                break;
            case R.id.roll_twice_button:
                rollTwice();
        }
    }

    private void addCustomDie() {
//        get custom die value
        getCustomDieValue = Integer.parseInt(customDieInput.getText().toString());

//        add to custom die to list
        spinnerList.add(getCustomDieValue);

//        notify adapter that the list has been updated
        spinnerAdapter.notifyDataSetChanged();

//        reset custom input
        customDieInput.setText("");
    }

    private void rollOnce() {
        Log.d("select", String.valueOf(selectedSpinnerOption));
        rollOnceDisplay.setText(String.valueOf(dice.getSideUp()));
        rollTwiceDisplay.setVisibility(View.GONE);

//        update history
        diceHistoryListArray.add(dice.getDiceName());
        historyAdapter.notifyDataSetChanged();
    }

    private void rollTwice() {
        Log.d("select", String.valueOf(selectedSpinnerOption));
//        set visibility true
        if (rollTwiceDisplay.getVisibility() == View.GONE) { rollTwiceDisplay.setVisibility(View.VISIBLE); }

//        display first value
        rollOnceDisplay.setText(String.valueOf(dice.getSideUp()));
        diceHistoryListArray.add(0, dice.getDiceName());

//        roll for second value
        dice.rollDice();

//        display second value
        rollTwiceDisplay.setText(String.valueOf(dice.getSideUp()));
        diceHistoryListArray.add(dice.getDiceName());
        historyAdapter.notifyDataSetChanged();
    }



//    private void saveData(){
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("iniii", 4);
//        Log.d("saveData", String.valueOf(spinnerList));
//        editor.putString("prefs_spinner_list", String.valueOf(spinnerList));
//        editor.apply();
//    }
}