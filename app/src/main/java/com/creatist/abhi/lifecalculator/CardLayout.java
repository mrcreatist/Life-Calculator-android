package com.creatist.abhi.lifecalculator;

/**
 * Created by Abhi on 12/6/2017.
 */

public class CardLayout {

    private String userNameString, dayValueString, monthValueString, horoscopeSign;
    private int horoscopeImage;

    public CardLayout(String userNameString, String dayValueString, String monthValueString, String horoscopeSign, int horoscopeImage) {
        this.userNameString = userNameString;
        this.dayValueString = dayValueString;
        this.monthValueString = monthValueString;
        this.horoscopeSign = horoscopeSign;
        this.horoscopeImage = horoscopeImage;
    }

    // getter

    public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getMonthValueString() {
        return monthValueString;
    }

    public void setMonthValueString(String monthValueString) {
        this.monthValueString = monthValueString;
    }

    public String getHoroscopeSign() {
        return horoscopeSign;
    }

    // setter

    public void setHoroscopeSign(String horoscopeSign) {
        this.horoscopeSign = horoscopeSign;
    }

    public String getDayValueString() {
        return dayValueString;
    }

    public void setDayValueString(String dayValueString) {
        this.dayValueString = dayValueString;
    }

    public int getHoroscopeImage() {
        return horoscopeImage;
    }

    public void setHoroscopeImage(int horoscopeImage) {
        this.horoscopeImage = horoscopeImage;
    }
}
