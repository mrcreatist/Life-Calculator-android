package com.example.abhi.lifecalculator;

/**
 * Created by Abhi on 12/6/2017.
 */

public class CardLayout {

    private String userNameString, dayValueString, monthValueString;

    public CardLayout(String userNameString, String dayValueString, String monthValueString) {
        this.userNameString = userNameString;
        this.dayValueString = dayValueString;
        this.monthValueString = monthValueString;
    }

    // getter

    public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getDayValueString() {
        return dayValueString;
    }

    // setter

    public void setDayValueString(String dayValueString) {
        this.dayValueString = dayValueString;
    }

    public String getMonthValueString() {
        return monthValueString;
    }

    public void setMonthValueString(String monthValueString) {
        this.monthValueString = monthValueString;
    }
}
