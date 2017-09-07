package com.gaodun.horizontalcalendar;

/**
 * Created by sniper on 2017/9/7.
 */

public class CalendarData
{
    private boolean isSelected;
    private String data;
    private String weekDayName;

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getWeekDayName()
    {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName)
    {
        this.weekDayName = weekDayName;
    }
}
