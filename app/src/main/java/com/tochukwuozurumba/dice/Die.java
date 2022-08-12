package com.tochukwuozurumba.dice;

import android.util.Log;

public class Die {
    private int diceSides;
    private int sideUp;
    private String diceName;


    public Die(int diceSides) {
        this.diceSides = diceSides;
        rollDice();
    }

    public int getSideUp () {
        return sideUp;
    }

    public String getDiceName() {
        return this.diceName;
    }

    public void setDiceName(String diceName) {
        this.diceName = diceName;
    }

    public void rollDice() {
        sideUp = (int) ((Math.random()) * diceSides) + 1;
        setDiceName("d" + sideUp);
        Log.d("die sideup", String.valueOf(sideUp));
        Log.d("die number", String.valueOf(diceSides));
        Log.d("TAG", diceName);
    }
}
