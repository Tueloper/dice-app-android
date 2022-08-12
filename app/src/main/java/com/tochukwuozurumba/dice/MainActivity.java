package com.tochukwuozurumba.dice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    declare global variables
    private Spinner spinner;
    private Button customDieButton;
    private Button rollButtonOnce;
    private Button rollButtonTwice;
    private TextView rollOnceDisplay;
    private TextView rollTwiceDisplay;
    private EditText customDieInput;
    private ArrayAdapter<Integer> spinnerAdapter;
    private ArrayList<Integer> formattedArray;
    private Integer getCustomDieValue;
    private ArrayList<Integer> spinnerList;
    private SharedPreferences prefs;
    private int selectedSpinnerOption;
    private Die dice;
    private ArrayAdapter<String> historyAdapter;
    private ArrayList<String> diceHistoryListArray;
    private ListView diceHistoryDisplay;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();

//        shared preferences
        prefs = this.getSharedPreferences("Data_Save", this.MODE_PRIVATE);

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerOption = spinnerList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        history
        diceHistoryListArray = new ArrayList<>();
        historyAdapter = new ArrayAdapter<String>(this, R.layout.recycler_view, diceHistoryListArray);
        diceHistoryDisplay = findViewById(R.id.view_history);
        diceHistoryDisplay.setAdapter(historyAdapter);
    }

    protected void onResume(){
        super.onResume();

        spinnerList = new ArrayList<Integer>();
        String stringSpinnerList = prefs.getString("spinnerList", "");

//        convert string to an array of integers;
        spinnerList = formatArray(stringSpinnerList);
        spinnerAdapter = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerAdapter);
    }

    protected void onPause() {
        saveData();
        Log.d("Pause", "App is paused");
        super.onPause();
    }

    protected void onStop() {
        saveData();
        super.onStop();
    }

    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        dice = new Die(selectedSpinnerOption);
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

//        sort array
        Collections.sort(spinnerList);

//        notify adapter that the list has been updated
        spinnerAdapter.notifyDataSetChanged();

//        reset custom input
        customDieInput.setText("");
    }

    private void rollOnce() {
        rollOnceDisplay.setText(String.valueOf(dice.getSideUp()));
        rollTwiceDisplay.setVisibility(View.GONE);

//        update history
        addDiceName();
        historyAdapter.notifyDataSetChanged();
    }

    private void rollTwice() {
//        set visibility true
        if (rollTwiceDisplay.getVisibility() == View.GONE) { rollTwiceDisplay.setVisibility(View.VISIBLE); }

//        display first value
        rollOnceDisplay.setText(String.valueOf(dice.getSideUp()));
        addDiceName();

//        roll for second value
        dice.rollDice();

//        display second value
        rollTwiceDisplay.setText(String.valueOf(dice.getSideUp()));
        addDiceName();
        historyAdapter.notifyDataSetChanged();
    }


    private void addDiceName() {
        diceHistoryListArray.add(0, dice.getDiceName());
    }

    private void saveData() {
        String json = gson.toJson(spinnerList);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("spinnerList", json);
        editor.commit();
    }

    private ArrayList<Integer> formatArray(String stringArray) {
        if (stringArray.isEmpty() || stringArray == "") {
            spinnerList.add(2);
            spinnerList.add(4);
            spinnerList.add(6);
            spinnerList.add(8);
        } else {
            Type type = new TypeToken<List<String>>() {}.getType();
            List<String> spinnerArr = gson.fromJson(stringArray, type);

            for (String num : spinnerArr) {
                spinnerList.add(Integer.parseInt(num));
            }
        }

        return spinnerList;
    }
}