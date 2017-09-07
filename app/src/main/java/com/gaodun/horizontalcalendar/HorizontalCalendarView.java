package com.gaodun.horizontalcalendar;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sniper on 2017/9/7.
 */

public class HorizontalCalendarView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private View root;
    private final int ITEM_SIZE = 50;
    private GridView gvCalendar;
    private ImageView imageRight, imgLeft;
    //当前日期
    private Calendar calendar;
    private TextView tvDateName;
    private List calendarDataList;
    private CalendarAdapter adapter;
    private int itemWidth;
    private HorizontalScrollView scrollView;
    private CalendarData lastSelData;
    private String[] weeksStr;

    public HorizontalCalendarView(Context context)
    {
        super(context);
        init();
    }

    public HorizontalCalendarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public HorizontalCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        root = LayoutInflater.from(getContext()).inflate(R.layout.calendarview, this);
        weeksStr = getResources().getStringArray(R.array.WEEK_DAYS);
        scrollView = (HorizontalScrollView) this.root.findViewById(R.id.scrollView);
        gvCalendar = (GridView) this.root.findViewById(R.id.gv_calendar);
        gvCalendar.setOnItemClickListener(this);
        adapter = new CalendarAdapter(getContext());
        gvCalendar.setAdapter(adapter);
        calendarDataList = new ArrayList();
        itemWidth = (int) (ITEM_SIZE * getResources().getDisplayMetrics().density);

        tvDateName = (TextView) root.findViewById(R.id.tvDateName);
        imageRight = (ImageView) root.findViewById(R.id.imgRight);
        imageRight.setOnClickListener(this);
        imgLeft = (ImageView) root.findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(this);

        calendar = Calendar.getInstance();

        setDateName();
        setCalendarDateList();
    }

    @Override
    public void onClick(View v)
    {
        int nowy = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowd = calendar.get(Calendar.DAY_OF_MONTH);

        switch (v.getId())
        {
            case R.id.imgRight:
                nowMonth++;
                if (nowMonth == 13)
                {
                    nowMonth = 0;
                    nowy++;
                }
                break;
            case R.id.imgLeft:
                nowMonth--;
                if (nowMonth == -1)
                {
                    nowMonth = 12;
                    nowy--;
                }
                break;
            default:
                break;
        }
        calendar.set(nowy, nowMonth, nowd);
        //update calendar
        setDateName();
        setCalendarDateList();
    }

    private void setCalendarDateList()
    {
        calendarDataList.clear();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < days; i++)
        {
            int nowy = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH);

            CalendarData data = new CalendarData();
            Calendar tempCalendar = (Calendar) calendar.clone();
            tempCalendar.set(nowy, nowMonth, i + 1);
            int weekdayIndex = tempCalendar.get(Calendar.DAY_OF_WEEK);
            data.setWeekDayName(weeksStr[weekdayIndex - 1]);
            calendarDataList.add(data);
            if (i % 10 == 0)
            {
                data.setData("test");
            }
        }
        adapter.replaceAll(calendarDataList);

        gvCalendar.setNumColumns(days);
        int itemSize = calendarDataList.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize * itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        gvCalendar.setLayoutParams(params);
        scrollTo(1);
    }

    private void setDateName()
    {
        int nowy = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        nowMonth++;
        String dateString = nowy + "." + nowMonth;
        tvDateName.setText(dateString);
    }


    public void scrollTo(int index)
    {//移动到指定位置
        if (index > 0)
        {
            final int scrollOffSet = itemWidth * index;
            new Handler().post(new Runnable()
            {
                @Override
                public void run()
                {
                    scrollView.scrollTo(scrollOffSet, 0);
                }
            });
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        if (lastSelData != null)
        {
            lastSelData.setSelected(false);
        }
        CalendarData data = (CalendarData) calendarDataList.get(i);
        data.setSelected(true);
        lastSelData = data;
        adapter.notifyDataSetChanged();
    }
}
