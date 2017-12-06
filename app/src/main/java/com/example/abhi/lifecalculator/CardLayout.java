package com.example.abhi.lifecalculator;

/**
 * Created by Abhi on 12/6/2017.
 */

public class CardLayout {

    private String userNameString, dayValueString, monthValueString, horoscopeSign;

    public CardLayout(String userNameString, String dayValueString, String monthValueString, String horoscopeSign) {
        this.userNameString = userNameString;
        this.dayValueString = dayValueString;
        this.monthValueString = monthValueString;
        this.horoscopeSign = horoscopeSign;
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

    public void setDayValueString(String dayValueString) {
        this.dayValueString = dayValueString;
    }

    // setter

    public String getMonthValueString() {
        return monthValueString;
    }

    public void setMonthValueString(String monthValueString) {
        this.monthValueString = monthValueString;
    }

    public String getHoroscopeSign() {
        return horoscopeSign;
    }

    public void setHoroscopeSign(String horoscopeSign) {
        this.horoscopeSign = horoscopeSign;
    }
}
